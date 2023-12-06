<template>
  <el-card class="box-card">

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
          <div v-if="slot.hasAppointment">
            {{ slot.course }}
            <br>
            {{ slot.tutor }}
            <!-- 如果在未来有location的信息，可以按如下方式添加 -->
            <br>
            {{ slot.location }}
          </div>
        </td>
      </tr>
      </tbody>
    </table>
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
  startTime: number;
  finishTime: number;
  isDelete: boolean;
  tname: string;
  sname: string;
  location: string;
}

type TableSlot = {
  hasAppointment: boolean;
  course?: string;
  tutor?: string;
  location?: string;
};

export default defineComponent({
  name: 'CourseTimeTableCard',
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
    const tableData = ref<TableSlot[][]>(Array(12).fill(0).map(() => Array(7).fill({ hasAppointment: false })));

    onMounted(async () => {
      let payload: { sid?: number; tid?: number } = {};
      console.log(props.usertype)

      if (props.usertype === 'student' && props.uid) {
        payload.sid = props.uid;
      } else if (props.usertype === 'tutor' && props.uid) {
        payload.tid = props.uid;
      }

      try {
        const response = await common('/api/appointment/findAppointmentBySelective', payload, 'POST');
        console.log(response)
        if (response.data.code === 1) {
          const currentTime = Date.now();
          response.data.data.forEach((item: CourseData) => {
            // If userType is 'student', course data is processed only when sid is equal to uid
            if (props.usertype !== 'student' || (props.usertype === 'student' && item.sid === props.uid && item.finishTime > currentTime)) {
              console.log("Displaying course in timetable:", item);
              const startHour = new Date(item.startTime).getHours();
              const dayOfWeek = new Date(item.startTime).getDay(); // 0-6, 0 is Sunday
              const slotIndex = Math.floor(startHour / 2);  // Assuming each slot is 2 hours
              tableData.value[slotIndex][dayOfWeek] = {
                hasAppointment: true,
                course: item.cname,
                tutor: item.tname,
                location: item.location,
              };
            }
          });
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    });

    return {
      tableData,
      input1
    }
  }
});
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');

.box-card {
  display: flex;
  flex-direction: column;
  width: 95%; /* This ensures it takes the full width of its parent */
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
  background-color: #38d39f;
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
</style>
