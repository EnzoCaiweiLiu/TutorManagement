<template>
  <el-card class="box-card">
    <template #header>
      <span>Support List</span>
    </template>

    <el-table :data="tableData" height="500" style="width: 100%">
      <el-table-column prop="sid" label="Support ID" />
      <el-table-column prop="email" label="Email" />
      <el-table-column prop="startTime" label="Founded Time" />
      <el-table-column prop="updatedTime" label="Updated Time" />

      <el-table-column>
        <template #default="scope">
          <el-button
              color="#95F9C4"
              type="primary"
              @click="openDetailDrawer(scope.row)">
            Details
          </el-button>
          <el-button type="primary" @click="completeSupport(scope.row)">Completed</el-button>
        </template>

      </el-table-column>
    </el-table>

    <!-- Edit Drawer -->
    <el-drawer v-model="detailDrawer" :user="currentSup" >
      <el-form label-position="top">

        <el-form-item label="Question Info">
          <el-input
              v-model="currentSup.info" readonly
              type="textarea"
              rows="5"
          />
        </el-form-item>

        <el-form-item label="Solutions:">
          <el-input
              v-model="currentSup.answer"
              type="textarea"
              rows="5"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="updateSupport">Submit</el-button>
        </el-form-item>

      </el-form>
    </el-drawer>

  </el-card>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import common from '@/api/commonAPI';
import { Ref } from 'vue';

interface SupportIn {
  sid: number;
  email: string;
  createdAt: number;
  updatedAt: number;
  info: string;
  answer: string;
}

export interface SuppEX {
  sid: number;
  email: string;
  createdTime: string;
  updatedTime: string;
  info: string;
  answer: string;
}

export default defineComponent({
  name: 'SupManageCard',

  components: {
  },

  setup() {
    const tableData: Ref<SuppEX[]> = ref([]);
    const detailDrawer = ref(false);
    const currentSup = ref<SuppEX>({} as SuppEX); // To store the current selected student's data

    const openDetailDrawer = (admin: SuppEX) => {
      console.log("Opening detail drawer...");
      currentSup.value = admin;
      detailDrawer.value = true;
      console.log(currentSup.value);
    }

    const updateSupport = async () => {
      if (!currentSup.value.sid) {
        console.error("No Support ID provided.");
        return;
      }

      try {
        const payload = {
          sid: currentSup.value.sid,
          info: currentSup.value.info,
          answer: currentSup.value.answer,
        };

        const updateResponse = await common('/api/support/updateSupport', payload, "POST");

        if (updateResponse.data.code === 1) {
          console.log("Update successful");

          const sendPayload = {
            email: currentSup.value.email,
            info: currentSup.value.info,
            answer: currentSup.value.answer,
          };

          const sendResponse = await common('/api/support/sendSupport', sendPayload, "POST");

          if (sendResponse.data.code === 1) {
            console.log("Email sent successfully");
            alert("Support updated and email sent!");
          } else {
            console.error("Failed to send email", sendResponse.data);
            alert("Support updated but failed to send email.");
          }

          detailDrawer.value = false;
        } else {
          console.error("Update failed", updateResponse.data);
        }
      } catch (error) {
        console.error("Error processing support update", error);
      }
    }


    const fetchTableData = async () => {
      try {
        const response = await common(
            '/api/support/queryAllSupport',
        );

        if (response.data.code === 1) {
          tableData.value = response.data.data.map((item: SupportIn) => ({
            sid: item.sid,
            email: item.email,
            startTime: new Date(item.createdAt).toLocaleDateString() + ' ' + new Date(item.createdAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
            updatedTime: new Date(item.updatedAt).toLocaleDateString() + ' ' + new Date(item.updatedAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
            info: item.info,
            answer: item.answer,
          }));
          console.log(response.data.data)
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    const completeSupport = async (sup: SuppEX) => {
      if (!sup.sid) {
        console.error("No Support ID provided.");
        return;
      }

      try {
        const response = await common('/api/support/deleteSupport', { sid: sup.sid }, "POST");

        if (response.data.code === 1) {
          console.log("Issue completed! Support deleted successfully");
          alert("Issue completed! Support deleted successfully!")
          await fetchTableData();
        } else {
          console.error("Deletion failed", response.data);
        }
      } catch (error) {
        console.error("Error deleting support", error);
      }
    }

    onMounted(fetchTableData);

    return {
      tableData,
      detailDrawer,
      currentSup,
      completeSupport,
      openDetailDrawer,
      updateSupport,
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
