<template>
  <el-card class="box-card">
    <template #header>
      <div class="header-content">
        <span>My Courses</span>
        <el-button @click="showDrawer = true" class="create-button">Create</el-button>
      </div>
    </template>

    <table class="time-table">
      <thead>
      <tr>
        <th>Time / Day</th>
        <th>Monday</th>
        <th>Tuesday</th>
        <th>Wednesday</th>
        <th>Thursday</th>
        <th>Friday</th>
        <th>Saturday</th>
        <th>Sunday</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="(row, rowIndex) in tableData" :key="rowIndex">
        <td>{{ rowIndex * 2 }}:00 - {{ rowIndex * 2 + 2 }}:00</td>
        <td v-for="(slot, colIndex) in row" :key="colIndex" :class="{ 'has-appointment': slot.hasAppointment }">
          <button v-if="slot.hasAppointment" @click="handleClick(slot, rowIndex, colIndex)">
            {{ slot.course }}
            <br>
            {{ slot.approve ? slot.student : ""}}
            <br>
            {{ slot.location }}
          </button>
        </td>
      </tr>
      </tbody>

    </table>
  </el-card>

  <el-drawer
      v-model="drawerVisible"
      size="40%"
      title="Details"
      @update:modelValue="handleCloseDrawer"
  >
    Content of the drawer
  </el-drawer>

  <el-drawer
      v-model="showDrawer"
      size="40%"
      title="Create New"
  >
    <el-form :model="form" label-width="120px">
      <el-form-item label="Course Name">
        <el-select v-model="form.cid" placeholder="Select a course">
          <el-option
              v-for="course in courses"
              :key="course.cid"
              :label="course.name"
              :value="course.cid"/>
        </el-select>
      </el-form-item>

      <el-form-item label="Tutor ID">
        <el-input v-model="form.tid" disabled placeholder="Enter Tutor ID"></el-input>
      </el-form-item>

      <el-form-item label="Start Time">
        <el-date-picker
            v-model="form.startTime"
            type="datetime"
            placeholder="Select Start Time">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="Finish Time">
        <el-date-picker
            v-model="form.finishTime"
            type="datetime"
            placeholder="Select Finish Time">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="Location">
        <el-input v-model="form.location" placeholder="Enter Location (e.g., Online)"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="success" @click="submitForm">Submit</el-button>
        <el-button @click="showDrawer = false">Cancel</el-button>
      </el-form-item>
    </el-form>

  </el-drawer>

</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import common from '@/api/commonAPI';
import { ElMessageBox } from 'element-plus';

interface CourseData {
  aid: number;
  csid: number;
  cname: string;
  tid: number;
  sid: number;
  startTime: number;
  finishTime: number;
  isDelete: boolean;
  tlname: string;
  slname: string;
  location: string;
  approve: boolean;
}

type TableSlot = {
  hasAppointment: boolean;
  course?: string;
  tutor?: string;
  student?: string;
  location?: string;
  approve?: boolean;
};

export default defineComponent({
  name: 'TutTimeTableCard',
  props: {
    userType: {
      type: String,
      required: true
    },
    uid: {
      type: Number,
      required: true
    }
  },
  setup(props) {
    const tableData = ref<TableSlot[][]>(Array(12).fill(0).map(() => Array(7).fill({ hasAppointment: false })));
    const showDrawer = ref(false);

    const courses = ref([]);

    const form = ref({
      cid: null,
      tid: props.uid,
      startTime: 1703911111100,
      finishTime: 1703986211100,
      location: "",
      isDelete: false
    });

    async function submitForm() {
      ElMessageBox.confirm('Are you sure you want to submit this course? All student can make an appointment after you submit!', 'Confirmation', {
        confirmButtonText: 'Yes',
        cancelButtonText: 'No',
        type: 'warning',
        distinguishCancelAndClose: true,
        confirmButtonClass: 'el-button--success' // Make the confirmation button green
      }).then(async () => {
        // Code executed when the user clicks "Confirm"
        try {
          const payload = {
            cid: Number(form.value.cid),
            tid: form.value.tid,
            startTime: form.value.startTime,
            finishTime: form.value.finishTime,
            location: form.value.location,
            isDelete: form.value.isDelete
          };

          //Print the sent data
          console.log("Sending form data:", payload);

          const response = await common(
              '/api/course/addCourse',
              payload,
              'POST'
          );

          // Print response data
          console.log("Response received:", response.data);

          // Process subsequent logic based on response
          if (response.data.code === 1) {
            console.log("Form submitted successfully:", response.data);
            showDrawer.value = false;
          } else {
            console.error("Error submitting form:", response.data.message);
          }
        } catch (error) {
          console.error("Error submitting form:", error);
        }
      }).catch(() => {
        // Code executed when the user clicks "Cancel" or clicks somewhere other than the pop-up window to close the pop-up window
        console.log('Form submission was cancelled.');
      });
    }


    onMounted(async () => {

      try {
        const courseResponse = await common('/api/course/queryAllCourseType');
        if (courseResponse.data.code === 1) {
          courses.value = courseResponse.data.data;
        }
      } catch (error) {
        console.error("Error fetching courses:", error);
      }

      try {
        const response = await common(
            '/api/appointment/findAppointmentBySelective',
            { tid: props.uid },
            'POST'
        );

        if (response.data.code === 1) {
          const currentTime = Date.now();
          response.data.data.forEach((item: CourseData) => {
            // Check if the course's finishTime is later than the current time
            if (item.finishTime > currentTime) {
              console.log("Displaying course in timetable:", item);
              const startHour = new Date(item.startTime).getHours();
              const dayOfWeek = new Date(item.startTime).getDay();
              const slotIndex = Math.floor(startHour / 2);  // Assuming each slot is 2 hours
              tableData.value[slotIndex][dayOfWeek] = {
                hasAppointment: true,
                course: item.cname,
                tutor: item.tlname,
                student: item.slname,
                location: item.location,
                approve: item.approve,
              };
            }
          });
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    });

    const drawerVisible = ref(false);
    function handleClick(slot: TableSlot, rowIndex: number, colIndex: number) {
      console.log(`Clicked on slot:`, slot, `at row:`, rowIndex, `and column:`, colIndex);
      drawerVisible.value = false;
    }

    function handleCloseDrawer(val: boolean) {
      drawerVisible.value = val;
    }

    return {
      tableData,
      handleClick,
      drawerVisible,
      handleCloseDrawer,
      showDrawer,
      form,
      submitForm,
      courses,
    }
  }
});
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');

.box-card {
  display: flex;
  flex-direction: column;
  width: 95%;
  margin: 2% auto;
  background: rgba(255, 255, 255, 0.7);
  border-radius: 15px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}

.time-table {
  width: 100%;
  border-collapse: collapse;
}

.time-table th, .time-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

.has-appointment {
}

.time-table {
  font-family: 'Arial', sans-serif;
  font-size: 14px;
}

.time-table .has-appointment {
  font-family: 'Arial', sans-serif;
  font-size: 14px;
}

.time-table .has-appointment .course {
  font-family: 'Arial', sans-serif;
  font-size: 16px;
}

.time-table .has-appointment .tutor {
  font-family: 'Arial', sans-serif;
  font-size: 12px;
}

.time-table button {
  width: 100%;
  padding: 2px;
  text-align: center;
  border: none;
  background-color: #38d39f;
  cursor: pointer;
  border-radius: 8px;
  transition: all 0.3s;
}

.time-table button:hover {
  background-color: rgba(0, 0, 0, 0.1);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.create-button {
  background-color: #38d39f;
  border-color: #38d39f;
}

.create-button:hover {
  background-color: rgba(0, 0, 0, 0.1);
  border-color: rgba(0, 0, 0, 0.1);
}
</style>
