<template>
  <el-card class="box-card">
    <template #header>
      <span>MyCourse List</span>
    </template>

    <!-- This is where your time table goes -->
    <el-table :data="courseTimeData" height="660">
      <el-table-column prop="course" label="Course" />
      <el-table-column prop="student" label="Student"  />
      <el-table-column prop="sid" label="Student ID"  />
      <el-table-column prop="location" label="Location"  />
      <el-table-column prop="approve" label="Approve"  />
      <el-table-column prop="startTime" label="Start Time" width="160" />
      <el-table-column prop="finishTime" label="Finish Time" width="160" />
      <el-table-column>
        <template #default="scope">
          <el-button @click="showDrawer(scope.row)">Edit</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-drawer
        v-model="drawer"
        title="I am the title"
        :with-header="false"
        size="40%"
    >
      <span>Hi there! This is detail for {{ currentRow.course }}</span>
      <div style="display: flex; flex-direction: column; align-items: center; justify-content: center; height: 100%;">

        <div class="course-info">
          <div class="course-item">
            <span class="course-label">Course Name:</span>
            <span class="course-value">{{ currentRow.course }}</span>
          </div>
          <div class="course-item">
            <span class="course-label">Student:</span>
            <span class="course-value">{{ currentRow.student }}</span>
          </div>
          <div class="course-item">
            <span class="course-label">Location:</span>
            <span class="course-value">{{ currentRow.location }}</span>
          </div>
          <div class="course-item">
            <span class="course-label">Start Time:</span>
            <span class="course-value">{{ currentRow.startTime }}</span>
          </div>
          <div class="course-item">
            <span class="course-label">Finish Time:</span>
            <span class="course-value">{{ currentRow.finishTime }}</span>
          </div>
        </div>

        <el-button
            :type="currentRow.approve ? 'danger' : 'success'"
            @click="handleApprovalToggle"
        >
          {{ currentRow.approve ? 'Unapprove' : 'Approve' }}
        </el-button>

      </div>
    </el-drawer>

  </el-card>
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
  location: string;
  tlname: string;
  slname: string;
  approve: boolean;
}

interface CurrentRowData {
  course?: string;
  tutor?: string;
  location?: string;
  startTime?: string;
  finishTime?: string;
  approve?: boolean;
  aid?: number;
}

export default defineComponent({
  name: 'TutAppointment',

  components: {
  },

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
    const courseTimeData = ref<Array<{
      course: string,
      tutor: string,
      location: string,
      startTime: string,
      finishTime: string,
      approve: boolean,
      aid: number,
    }>>([]);

    const drawer = ref(false); // Define the state of the drawer
    const currentRow = ref<CurrentRowData>({}); // Store the current clicked row's data


    const showDrawer = (row: any) => {
      currentRow.value = row;
      drawer.value = true; // Open the drawer
    }

    onMounted(async () => {

      try {
        const response = await common(
            '/api/appointment/findAppointmentBySelective',
            {
              tid: props.uid
            },
            'POST'
        );

        if (response.data.code === 1) {
          console.log(response.data.data)

          const currentTime = Date.now();

          const filteredData = response.data.data
              .filter((item: CourseData) => item.finishTime > currentTime);
          console.log(filteredData)

          courseTimeData.value = filteredData.map((item: CourseData) => ({
            course: item.cname,
            student: item.slname,
            sid: item.sid,
            tutor: item.tlname,
            location: item.location,
            approve: item.approve,
            aid: item.aid,
            startTime: new Date(item.startTime).toLocaleDateString() + ' ' + new Date(item.startTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
            finishTime: new Date(item.finishTime).toLocaleDateString() + ' ' + new Date(item.finishTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
          }));
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    });

    const handleApprovalToggle = async () => {
      try {
        // Pop-up window confirmation
        await ElMessageBox.confirm(
            `Are you sure you want to ${currentRow.value.approve ? 'unapprove' : 'approve'} this appointment?`,
            'Confirmation',
            {
              confirmButtonText: 'OK',
              cancelButtonText: 'Cancel',
              type: 'warning'
            }
        );

        // If the user confirms, continue to perform subsequent operations
        const apiUrl = currentRow.value.approve
            ? "/api/appointment/unapproved"
            : "/api/appointment/approved";

        const response = await common(
            apiUrl,
            {
              aid: currentRow.value.aid, // Use the aid attribute of currentRow
              approve: !currentRow.value.approve
            },
            'POST'
        );

        if (response.data.code === 1) {
          currentRow.value.approve = !currentRow.value.approve;
          drawer.value = false;
        } else {
          console.error("Error updating approval status:", response.data.message);
        }
      } catch (error) {
        if (error !== 'cancel') {
          console.error("Error updating approval status:", error);
        }
      }
    };


    return {
      courseTimeData,
      drawer, // expose drawer state to the template
      currentRow, // expose currentRow to the template
      showDrawer,
      handleApprovalToggle,
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
  width: 10px;
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

.el-button--danger {
  background-color: #f56c6c;
  border-color: #f56c6c;
}

.el-button--danger:hover {
  background-color: #dd3333;
  border-color: #dd3333;
}

.el-drawer__header {
  background: rgba(255, 255, 255, 0.7);
  border-radius: 15px 15px 0 0;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}

.el-drawer__body {
  background: rgba(255, 255, 255, 0.7);
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}



</style>