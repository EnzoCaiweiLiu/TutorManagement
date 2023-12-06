<template>
  <el-card class="box-card">

    <!-- This is where your time table goes -->
    <el-table :data="courseTimeData" height="660">
      <el-table-column prop="course" label="Course"  />
      <el-table-column prop="student" label="Student"  />
      <el-table-column prop="location" label="Location"  />
      <el-table-column prop="startTime" label="Start Time"  />
      <el-table-column prop="finishTime" label="Finish Time"  />
      <el-table-column label="Status">
        <template #default="scope">
          {{ scope.row.approve ? 'Approved' : 'Pending' }}
        </template>
      </el-table-column>
      <el-table-column>
        <template #default="scope">
          <el-button @click="showDrawer(scope.row)">Check Feedback!</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-drawer
        v-model="drawer"
        title="Course Feedback"
        :with-header="false"
        size="40%"
    >
      <div style="display: flex; flex-direction: column; align-items: center; justify-content: center; height: 100%;">
        <span class="span">Feedback for the course:</span><br>
        <el-rate v-model="userRating" :max="5" show-score disabled />
        <el-input
            v-model="feedbackText"
            type="textarea"
            rows="5"
            placeholder="No feedback provided."
            class="feedback-input"
            disabled
        />
      </div>
    </el-drawer>

  </el-card>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import common from '@/api/commonAPI';

interface CourseData {
  aid: number;
  csid: number;
  cname: string;
  tid: number;
  sid: number;
  location: string;
  startTime: number;
  finishTime: number;
  isDelete: boolean;
  tlname: string;
  slname: string;
  approve: boolean;
}

export default defineComponent({
  name: 'TutLearningHistory',

  props: {
    usertype: {
      type: String,
      required: true
    },
    uid: {
      type: Number,
      required: true
    }
  },

  setup(props) {
    const input1 = ref('');
    const courseTimeData = ref<Array<{
      course: string,
      student: string,
      location: string,
      startTime: string,
      finishTime: string }>>([]);
    const drawer = ref(false);// Switch used to control the drawer
    const currentRow = ref<CourseData | null>(null);
    const compileTime = new Date();
    const userRating = ref(0); // User rating
    const feedbackText = ref(''); //User text description


    const showDrawer = async (row: any) => {
      currentRow.value = row;
      try {
        const response = await common(
            '/api/rate/findRateBySelective',
            { aid: row.aid },
            'POST'
        );
        console.log("Full API Response:", response);

        if (response.data.code === 1 && response.data.data.length > 0) {
          const latestData = response.data.data[response.data.data.length - 1];// Get the last item of the array
          console.log("Latest Data:", latestData);

          userRating.value = latestData.rate || 'No rating provided.'; // Use the latest data
          feedbackText.value = latestData.feedback || 'No feedback provided.';
          drawer.value = true;

          console.log("API Response rate:", userRating.value);
          console.log("API Response feedback:", feedbackText.value);
        } else {
          console.error('Error fetching feedback:', response.data.msg || 'Unknown error.');
        }
      } catch (error) {
        console.error('API call error:', error);
      }
    };

    onMounted(async () => {

      try {
        const response = await common(
            '/api/appointment/findAppointmentBySelective',
            {
              tid: props.uid,
            },
            'POST');
        console.log(response.data.data)
        console.log(props.uid)

        if (response.data.code === 1) {
          console.log('Compile Time:', compileTime);
          const currentTime = Date.now();
          // First, filter the data whose sid is equal to props.uid
          const filteredData = response.data.data
              .filter((item: CourseData) => item.sid === props.uid && item.finishTime < currentTime);

          courseTimeData.value = filteredData.map((item: CourseData) => ({
            course: item.cname,
            student: item.slname,
            location: item.location,
            startTime: new Date(item.startTime).toLocaleDateString() + ' ' + new Date(item.startTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
            finishTime: new Date(item.finishTime).toLocaleDateString() + ' ' + new Date(item.finishTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
            approve: item.approve,
            aid: item.aid
          }));
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    });

    return {
      courseTimeData,
      input1,
      drawer,
      showDrawer,
      userRating,
      feedbackText,
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

.feedback-input {
  margin-top: 30px;
  height: 30%;
  width: 80%;
  line-height: 1.5;
  resize: none;
}

.span {
  font-size: 24px;
  margin-bottom: 10px;
}

.el-table .el-table__row:hover {
  background-color: rgba(0, 0, 0, 0.02);
}
</style>