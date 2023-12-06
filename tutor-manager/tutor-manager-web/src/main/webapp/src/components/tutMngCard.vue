<template>
  <el-card class="box-card">
    <template #header>
      <span>Tutor List</span>
    </template>

    <el-table :data="tableData" height="500" style="width: 100%">
      <el-table-column prop="tid" label="Tutor ID" />
      <el-table-column prop="lastName" label="Tutor Name" />
      <el-table-column prop="email" label="Email" />
      <el-table-column prop="password" label="Password" />

      <el-table-column label="Options">
        <template #default="scope">
          <el-button
              color="#95F9C4"
              type="primary"
              @click="openEditDrawer(scope.row)">
            Edit
          </el-button>
          <el-button
              color="#95F9C4"
              type="primary"
              @click="openDetailDrawer(scope.row)">
            Detail
          </el-button>
        </template>

      </el-table-column>
    </el-table>

    <!-- Edit Drawer -->
    <stu-m-e-drawer v-model="editDrawer" :student="currentTutor" @update:visible="editDrawer = $event"/>
    <stu-m-d-drawer v-model="detailDrawer" :student="currentTutor" />

  </el-card>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import common from '@/api/commonAPI';
import { Ref } from 'vue';
import stuMDDrawer from "@/components/stuMDDrawer.vue";
import stuMEDrawer from "@/components/stuMEDrawer.vue";

interface TutorAC {
  uid: number;
  firstName: string;
  middleName: string;
  lastName: string;
  gender: string;
  dob: number;
  email: string;
  password: string;
  isDelete: boolean;
}

export interface TutACItem {
  tid: number;
  firstname: string;
  middlename: string;
  lastname: string;
  Email: string;
  password: string;
}

export default defineComponent({
  name: 'TutManageCard',

  components: {
    stuMDDrawer,
    stuMEDrawer,
  },

  setup() {
    const tableData: Ref<TutACItem[]> = ref([]);
    const editDrawer = ref(false);
    const detailDrawer = ref(false);
    const currentTutor = ref({}); // To store the current selected student's data

    const openEditDrawer = (tutor: TutACItem) => {
      console.log("Opening edit drawer...");
      currentTutor.value = tutor;
      editDrawer.value = true;
    }

    const openDetailDrawer = (tutor: TutACItem) => {
      console.log("Opening detail drawer...");
      currentTutor.value = tutor;
      detailDrawer.value = true;
    }

    const fetchTableData = async () => {
      try {
        const response = await common(
            '/api/tutor/queryAllTutor',
        );

        if (response.data.code === 1) {
          tableData.value = response.data.data.map((item: TutorAC) => ({
            tid: item.uid,
            firstName: item.firstName,
            middleName: item.middleName,
            lastName: item.lastName,
            email: item.email,
            password: item.password,
          }));
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    onMounted(fetchTableData);

    return {
      tableData,
      editDrawer,
      detailDrawer,
      currentTutor,
      openEditDrawer,
      openDetailDrawer,
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
</style>
