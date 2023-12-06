<template>
  <div class="feedback-container">
    <el-table :data="userFeedbackData" style="width: 100%">
      <el-table-column prop="course" label="Course Name"></el-table-column>
      <el-table-column prop="tutor" label="Tutor Name"></el-table-column>
      <el-table-column prop="rating" label="Rating">
        <template #default="scope">
          <el-rate
              v-model="scope.row.rating"
              disabled
              show-score
              text-color="#ff9900"
              score-template="{value} points"
          />
        </template>
      </el-table-column>
      <el-table-column prop="feedback" label="Feedback"></el-table-column>
    </el-table>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import common from '@/api/commonAPI';

interface feedback {
  sid: number;
  cname: string;
  tlname: string;
  location: string;
  finishTime: number;
  rate: number;
  feedback: string;
}

export default defineComponent({
  name: 'FeedbackViewer',
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
    const userFeedbackData = ref<feedback[]>([]);

    onMounted(async () => {
      try {
        const response = await common('api/rate/findRateBySelective', { sid: props.uid }, 'POST');
        if (response.data.code === 1) {
          console.log(response)
          userFeedbackData.value = response.data.data
              .filter((feedback: feedback) => feedback.sid === props.uid)
              .map((feedback: feedback) => ({
                course: feedback.cname,
                tutor: feedback.tlname,
                feedback: feedback.feedback,
                rating: feedback.rate,
              }));
        } else {
          console.error('Error fetching feedback:', response.data.message || 'Unknown error.');
        }
      } catch (error) {
        console.error('API call error:', error);
      }
    });

    return {
      userFeedbackData
    }
  }
});
</script>

<style scoped>
.feedback-container {
  padding: 20px;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 15px;
}

body {
  background: linear-gradient(to right, #a8c0ff, #3f2b96);
}

.el-button:hover {
  background-color: #5f4bb6;
  box-shadow: 0 4px 8px rgba(0,0,0,0.2);
}

.el-table .el-table__row:hover {
  background-color: rgba(0, 0, 0, 0.02);
}
</style>
