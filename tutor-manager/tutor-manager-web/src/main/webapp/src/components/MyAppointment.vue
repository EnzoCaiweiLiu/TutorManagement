<template>
  <el-card class="box-card">

    <!-- This is where your time table goes -->
    <el-table :data="courseTimeData" height="660">
      <el-table-column prop="course" label="Course"  />
      <el-table-column prop="tutor" label="Tutor"  />
      <el-table-column prop="tid" label="Tutor ID"  />
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
          <el-button @click="showDrawer(scope.row)">Cancel</el-button>
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
        <span class="span">Attention!</span><br>
        <span class="span">You are about to cancel a course appointment. Please ensure you have read following content and reconsider your decision to cancel:</span><br><br><br>

        <el-alert
            class="larger-description"
            type="info"
            description="Upon clicking 'Cancel Appointment', the tutor will be notified of your cancellation. This booking will be removed, and if you wish to book it again in the future, you will need to undergo the booking process once more."
            show-icon
            :closable="false"
        ></el-alert>

        <el-divider></el-divider>

        <el-alert
            class="larger-description"
            type="warning"
            description="After cancellation, you may not be able to book the same course during the same time slot. Please be certain of your decision."
            show-icon
            :closable="false"
        ></el-alert>

        <el-divider></el-divider>

        <el-button type="primary" @click="cancelAppointment">Confirm Cancellation</el-button>
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
  name: 'MyAppointment',

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
          const currentTime = Date.now();
          // 首先，过滤sid等于props.uid的数据
          const filteredData = response.data.data
              .filter((item: CourseData) => item.sid === props.uid && item.finishTime > currentTime);

          courseTimeData.value = filteredData.map((item: CourseData) => ({
            course: item.cname,
            tutor: item.tlname,
            tid: item.tid,
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

    const cancelAppointment = async () => {
      if (!currentRow.value) {
        console.warn('No row selected for cancellation.');
        return;
      }

      const payload = {
        aid: currentRow.value.aid,
      };

      try {
        console.log(payload)
        const response = await common('/api/appointment/deleteAppointment', payload, 'POST');
        if (response.data.code === 1) {
          // Handle success, maybe refresh the table or give a success message
          console.log("Appointment cancellation was successful.");
          alert('Your appointment is already cancelled!');
          drawer.value = false; // Close the drawer after success
        } else {
          console.error("Error canceling appointment:", response.data.message || 'Unknown error.');
        }
      } catch (error) {
        console.error("API call error:", error);
      }
    };

    return {
      courseTimeData,
      input1,
      drawer,
      showDrawer,
      cancelAppointment
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
</style>

