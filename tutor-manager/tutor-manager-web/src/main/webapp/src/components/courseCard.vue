<template>
  <el-card class="box-card">
      <template #header>
        <div>
          <span>Course List</span>
          <div class="filters">
            <el-autocomplete
                v-model="selectedCourse"
                :fetch-suggestions="querySearchCourses"
                placeholder="Filter by Course"
                @input="handleSelection"
            />

            <el-autocomplete
                v-model="selectedTutor"
                :fetch-suggestions="querySearchTutors"
                placeholder="Filter by Tutor"
                @input="handleSelection"
            />

            <el-autocomplete
                v-model="selectedLocation"
                :fetch-suggestions="querySearchLocations"
                placeholder="Filter by Location"
                @input="handleSelection"
            />

            <el-input-number
                v-model="selectedRate"
                @change="handleSelection"
                placeholder="Minimum Rate"
                :min="0"
                :max="5"
            />
          </div>
        </div>
      </template>

      <!-- This is where your table goes -->
      <el-table :data="tableData">
        <el-table-column prop="course" label="Course" width="140" />
        <el-table-column prop="tutor" label="Tutor" width="120" />
        <el-table-column prop="location" label="Address" width="160" />
        <el-table-column prop="startTimeFormatted" label="Start time" width="160" />
        <el-table-column prop="finishTimeFormatted" label="Finish time" width="160" />
        <el-table-column prop="rate" label="Rate">
          <template #default="scope">
            <el-rate
                v-model="scope.row.rate"
                disabled
                show-score
                text-color="#ff9900"
                score-template="{value} points"
            />
          </template>
        </el-table-column>
        <el-table-column>
          <template #default="scope">
            <el-button @click="showDrawer(scope.row)">Make Appointment</el-button>
          </template>
        </el-table-column>
      </el-table>

    <el-drawer
        v-model="drawer"
        title="Course Details"
        :with-header="false"
        size="40%"
    >
      <div style="display: flex; flex-direction: column; align-items: center; justify-content: center; height: 100%;">
        <span class="span">Fantastic!</span><br>
        <span class="span"> We are so happy you can choose a course to study! Don't forget to confirm the following course information: </span><br>
        <div class="course-info">
          <div class="course-item">
            <span class="course-label">Course Name:</span>
            <span class="course-value">{{ currentRow.course }}</span>
          </div>
          <div class="course-item">
            <span class="course-label">Tutor:</span>
            <span class="course-value">{{ currentRow.tutor }}</span>
          </div>
          <div class="course-item">
            <span class="course-label">Location:</span>
            <span class="course-value">{{ currentRow.location }}</span>
          </div>
          <div class="course-item">
            <span class="course-label">Start Time:</span>
            <span class="course-value">{{ currentRow.startTimeFormatted }}</span>
          </div>
          <div class="course-item">
            <span class="course-label">Finish Time:</span>
            <span class="course-value">{{ currentRow.finishTimeFormatted }}</span>
          </div>
        </div>

        <el-alert
            class="larger-description"
            type="info"
            description="Upon clicking 'Booking!', the tutor will receive your application. The tutor will process your application as soon as possible. The course will appear in your 'My Appointment' list and the status will be Approved after tutor approved you application."
            show-icon
            :closable="false"
        />

        <el-divider/>

        <el-alert
            class="larger-description"
            type="warning"
            description="Please ensure you don't apply for more than one course within the same time slot."
            show-icon
            :closable="false"
        />

        <el-divider/>

        <el-button type="primary" @click="bookAppointment">Booking!</el-button>
      </div>
    </el-drawer>


  </el-card>
</template>

<script lang="ts">
import { defineComponent, ref, PropType } from 'vue';
import { onMounted } from 'vue'; // Add onMounted
import common from '@/api/commonAPI'; // Import your API function
import { Ref } from 'vue';
import { watch } from 'vue';

interface CourseData {
  csid: number;
  cid: number;
  cname: string;
  tid: number;
  startTime: number;
  finishTime: number;
  location: string;
  isDelete: false,
  tlname: string;
  rate?: number;
}

interface TableDataItem {
  course: string;
  tutor: string;
  location: string;
  startTimeFormatted: number;
  finishTimeFormatted: number;
  rate: number;
  csid?: number;
  tid: number;
  startTime: number;  // Assuming this is a timestamp
  finishTime: number; // Add this line if it's not already there
}


export default defineComponent({
  name: 'TableCard',
  props: {
    uid: {
      type: Number as PropType<number>,
      required: true
    },
    usertype: {
      type: String as PropType<string>,
      required: true
    }
  },
  mounted() {
    console.log(this.usertype, this.uid);
  },

  setup(props) {
    const tableData: Ref<TableDataItem[]> = ref([]); // Initialize as an empty array

    const selectedCourse = ref('');
    const selectedTutor = ref('');
    const selectedLocation = ref('');
    const selectedRate = ref(null);
    const drawer = ref(false);
    const currentRow = ref<CourseData | null>(null);

    const showDrawer = (row: any) => {
      currentRow.value = row;
      drawer.value = true; // Open drawer
    };

    const fetchRates = async (tableItems: TableDataItem[]) => {
      const ratePromises = tableItems.map(item => {
        return common('/api/rate/findRateBySelective', { csid: item.csid }, 'POST')
            .then(response => {
              if (response.data.code === 1) {
                // test:
                // console.log(`Rate fetched for csid ${item.csid}:`, response.data.data, response.data.data[0].rate );
                return response.data.data[0].rate; // return the rate for the current item
              }
              return item.rate; // or return the existing rate if no new data fetched
            })
            .catch(error => {
              console.error(`Error fetching rate for csid ${item.csid}:`, error);
              return item.rate; // in case of error, return the existing rate
            });
      });

      const rates = await Promise.all(ratePromises);
      // Map the rates back to the table items
      return tableItems.map((item, index) => ({ ...item, rate: rates[index] || item.rate }));
    };

    const filterTableData = async () => {
      try {
        const requestData = {
          cname: selectedCourse.value || undefined,
          tlname: selectedTutor.value || undefined,
          location: selectedLocation.value || undefined
        };

        const response = await common(
            '/api/course/findCourseBySelective',
            requestData,
            "POST"
        );

        if (response.data.code === 1) {
          let updatedTableData = response.data.data.map((item: CourseData) => ({
            course: item.cname,
            tutor: item.tlname,
            location: item.location,
            csid: item.csid,
            tid: item.tid,
            startTimeFormatted: new Date(item.startTime).toLocaleDateString() + ' ' + new Date(item.startTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
            finishTimeFormatted: new Date(item.finishTime).toLocaleDateString() + ' ' + new Date(item.finishTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
            startTime: item.startTime,  // Keep original timestamp
            finishTime: item.finishTime,  // Keep original timestamp
            rate: item.rate || 0, // Assign 0 if no rate is present
          }));

          updatedTableData = await fetchRates(updatedTableData);

          const currentTime = Date.now();
          tableData.value = updatedTableData.filter((item: TableDataItem) => item.finishTime > currentTime);
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    const filterByRate = (minRate: number) => {
      // Ensure minRate is a number
      minRate = Number(minRate);

      // Filter tableData by the minimum rate
      tableData.value = tableData.value.filter(item => {
        // Treat undefined rate as 0
        const rate = item.rate ?? 0;
        return rate >= minRate;
      });
    };
    const handleSelection = () => {
      if (!selectedCourse.value && !selectedTutor.value && !selectedLocation.value && !selectedRate.value) {
        // Reset all filters and get all data
        filterTableData();
      } else {
        // Use the filters to get specific data
        filterTableData();
      }
      // After fetching and setting the table data, apply the rating filter
      // Ensure we pass a number to filterByRate, defaulting to 0 if null or undefined
      filterByRate(selectedRate.value ?? 0);
    }

// When the selectedRate changes, we need to filter the table data again.
    watch(selectedRate, (newValue) => {
      filterTableData().then(() => {
        // Ensure we pass a number to filterByRate, defaulting to 0 if null or undefined
        filterByRate(newValue ?? 0);
      });
    });

    onMounted(() => {
      filterTableData();
    });

    /* eslint-disable no-unused-vars */
    const querySearchCourses = (queryString: string, callback: (suggestions: { value: string }[]) => void) => {
      const courses = [...new Set(tableData.value.map(data => data.course))];
      const results = courses
          .filter(course => course.toLowerCase().includes(queryString.toLowerCase()))
          .map(item => ({ value: item }));
      callback(results);
    };

    const querySearchTutors = (queryString: string, callback: (suggestions: { value: string }[]) => void) => {
      const tutors = [...new Set(tableData.value.map(data => data.tutor))];
      const results = tutors
          .filter(tutor => tutor.toLowerCase().includes(queryString.toLowerCase()))
          .map(item => ({ value: item }));
      callback(results);
    };

    const querySearchLocations = (queryString: string, callback: (suggestions: { value: string }[]) => void) => {
      const locations = [...new Set(tableData.value.map(data => data.location))];
      const results = locations
          .filter(location => location.toLowerCase().includes(queryString.toLowerCase()))
          .map(item => ({ value: item }));
      callback(results);
    };
    /* eslint-enable no-unused-vars */
    const bookAppointment = async () => {
      if (props.usertype !== 'student') {
        console.error('User is not a student');
        return;
      }

      if (!props.uid) {
        console.error('Student UID is missing or null');
        return;
      }

      const sid = props.uid;

      const payload = {
        csid: currentRow.value?.csid,
        tid: currentRow.value?.tid,
        sid: sid,
        startTime: currentRow.value?.startTime,
        finishTime: currentRow.value?.finishTime,
      }

      console.log(payload)

      try {
        const response = await common('/api/appointment/addAppointment', payload, 'POST');
        if (response.data.code === 1) {
          console.log('Booking successful!');
          alert('Booking successful! Request send to tutor!');
          drawer.value = false;
        } else {
          console.error('Error booking:', response.data.message);
        }
      } catch (error) {
        console.error('Error during booking:', error);
      }
    };

    return {
      tableData,
      selectedCourse,
      selectedTutor,
      selectedLocation,
      selectedRate,
      querySearchCourses,
      querySearchTutors,
      querySearchLocations,
      handleSelection,
      drawer,
      currentRow,
      showDrawer,
      bookAppointment,
    }
  }
});
</script>

<style scoped>
.box-card {
  display: flex;
  flex-direction: column;
  width: 95%;
  margin: 2% auto;
  background: rgba(255, 255, 255, 0.7);
  border-radius: 15px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}

body {
  background: linear-gradient(to right, #a8c0ff, #3f2b96);
}

.el-button {
  background-color: #38d39f;
  color: #FFF;
  border-radius: 8px;
  margin-left: 5px;
  transition: all 0.3s;
}

.el-button:hover {
  background-color: #5f4bb6;
  box-shadow: 0 4px 8px rgba(0,0,0,0.2);
}

.el-table .el-table__row:hover {
  background-color: rgba(0, 0, 0, 0.02);
}

.course-info {
  padding: 10px;
  background: rgba(255, 255, 255, 0.7);
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 380px;
  margin-bottom: 30px;
}

.course-item {
  display: flex;
  justify-content: space-between;
  padding: 5px 0;
  border-bottom: 1px solid #eaeaea;
  font-size: 18px;
  margin-bottom: 15px;
}

.course-label {
  font-weight: 600;
  color: #333;
  margin-right: 10%;
}

.course-value {
  text-align: right;
  margin-left: 10%;
}

.course-item:last-child {
  border-bottom: none;
}

.span {
  display: flex;
  justify-content: space-between;
  padding: 5px 0;
  font-size: 23px;
  margin-bottom: 15px;
}
</style>
