<template>
  <el-card class="box-card">
    <template #header>
      <span>Course List</span>
      <el-button
          style="margin-left: 80%;"
          type="primary"
          @click="openAddDrawer">
        Add Course
      </el-button>
    </template>

    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="cid" label="Course ID" />
      <el-table-column prop="name" label="Course Name" />

      <el-table-column label="Options">
        <template #default="scope">
          <el-button
              color="#95F9C4"
              type="primary"
              @click="deleteCourse(scope.row.cid)">
            Delete
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Add Course Drawer -->
    <el-drawer
        title="Add New Course"
        v-model="addDrawer"
        size="30%">
      <el-input v-model="newCourseName" placeholder="Course Name"></el-input>
      <el-button
          style="margin-top: 10px;"
          type="success"
          @click="addCourse">
        Add
      </el-button>
    </el-drawer>

  </el-card>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import common from '@/api/commonAPI';

export default defineComponent({
  name: 'CourseManageCard',

  setup() {
    const tableData = ref([]);
    const addDrawer = ref(false);
    const newCourseName = ref('');

    const openAddDrawer = () => {
      addDrawer.value = true;
    }

    const fetchTableData = async () => {
      try {
        const response = await common('/api/course/queryAllCourseType');
        if (response.data.code === 1) {
          tableData.value = response.data.data;
          console.log(response)
        }
      } catch (error) {
        console.error("Error fetching course data:", error);
      }
    };

    const addCourse = async () => {
      try {
        const response = await common('/api/course/addCourseType', { name: newCourseName.value }, 'POST');
        newCourseName.value = '';
        addDrawer.value = false;
        if (response.data.code === 1) {
          console.log("course added", response.data);
        }
        fetchTableData();
      } catch (error) {
        console.error("Error adding course:", error);
      }
    }

    const deleteCourse = async (cid) => {
      try {
        const response = await common('/api/course/deleteCourseType', { cid }, 'POST');
        if (response.data.code === 1) {
          console.log("course deleted", response.data);
          alert("Course deleted!");
          fetchTableData();
        }
      } catch (error) {
        console.error("Error deleting course:", error);
      }
    }

    onMounted(fetchTableData);

    return {
      tableData,
      addDrawer,
      newCourseName,
      openAddDrawer,
      addCourse,
      deleteCourse,
    }
  }
});
</script>

<style scoped>
body {
  background: linear-gradient(to right, #a8c0ff, #3f2b96);
}

.box-card {
  display: flex;
  flex-direction: column;
  width: 95%;
  margin: 2% auto;
  background: rgba(255, 255, 255, 0.7);
  border-radius: 15px;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
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

.create-button {
  background-color: #38d39f;
  border-color: #38d39f;
  margin-left: 80%;
}

.create-button:hover {
  background-color: rgba(0, 0, 0, 0.1);
  border-color: rgba(0, 0, 0, 0.1);
}
</style>
