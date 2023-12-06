<template>
  <el-card class="box-card">

    <!-- This is where your time table goes -->
    <el-table :data="courseTimeData" height="660">
      <el-table-column prop="course" label="Course"  />
      <el-table-column prop="tutor" label="Tutor"  />
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
          <el-button @click="showDrawer(scope.row)">Make a Feedback!</el-button>
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
        <span class="span">Congratulations on completing your course! </span><br>
        <span>We value your feedback and would greatly appreciate it if you could take a moment to provide us with your thoughts and suggestions about the course.</span><br>
        <span>Your feedback will help us improve our services and make the learning experience even better. Thank you for your participation, </span><br>
        <span>and we look forward to hearing from you!</span><br>
        <p style="text-align: center; font-size: 16px;">
          Please provide your feedback for the course.
        </p>
        <el-rate v-model="userRating" :max="5" show-score  />
        <el-input
            v-model="feedbackText"
            type="textarea"
            rows="5"
            placeholder="Provide your feedback here"
            class="feedback-input"
        />
        <el-button type="primary" @click="submitFeedback">Submit Feedback</el-button>
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
  sname: string;
  approve: boolean;
}

export default defineComponent({
  name: 'LearningHistory',

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
    const courseTimeData = ref<Array<{ course: string, tutor: string, location: string, startTime: string, finishTime: string }>>([]);
    const drawer = ref(false);
    const currentRow = ref<CourseData | null>(null);
    const compileTime = new Date();
    const userRating = ref(0);
    const feedbackText = ref('');

    const submitFeedback = async () => {
      if (!currentRow.value || userRating.value === 0 || feedbackText.value.trim() === '') {
        console.warn('Please provide a valid rating and feedback text.');
        return;
      }

      const payload = {
        aid: currentRow.value.aid,
        rate: userRating.value,
        feedback: feedbackText.value
      };

      try {
        const response = await common('/api/rate/addRate', payload, 'POST');
        if (response.data.code === 1) {
          console.log('Feedback submitted successfully.');
          alert('Thank you for your feedback!');
          drawer.value = false; // 关闭抽屉
        } else {
          console.error('Error submitting feedback:', response.data.message || 'Unknown error.');
        }
      } catch (error) {
        console.error('API call error:', error);
      }
    };

    const showDrawer = (row: any) => {
      currentRow.value = row;
      drawer.value = true;
    };

    onMounted(async () => {
      let payload: { sid?: number; tid?: number } = {};

      if (props.usertype === 'student' && props.uid) {
        payload.sid = props.uid;
      } else if (props.usertype === 'tutor' && props.uid) {
        payload.tid = props.uid;
      }

      try {
        const response = await common('/api/appointment/findAppointmentBySelective', payload, 'POST');
        console.log(response.data.data)
        console.log(props.uid)

        if (response.data.code === 1) {
          console.log('Compile Time:', compileTime);
          const currentTime = Date.now();
          const filteredData = response.data.data
              .filter((item: CourseData) => item.sid === props.uid && item.finishTime < currentTime);

          courseTimeData.value = filteredData.map((item: CourseData) => ({
            course: item.cname,
            tutor: item.tlname,
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
      submitFeedback
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

/* 整体页面背景 */
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

/* 表格行悬停效果 */
.el-table .el-table__row:hover {
  background-color: rgba(0, 0, 0, 0.02);
}
</style>