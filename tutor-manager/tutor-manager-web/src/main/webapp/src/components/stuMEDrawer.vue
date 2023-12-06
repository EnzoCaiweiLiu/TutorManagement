<template>
  <el-drawer :visible="visible" @close="closeDrawer" :with-header="false" size="40%">
    <span>Details for {{ localStudent.firstname }} {{ localStudent.middlename }} {{ localStudent.lastname }}</span>

    <!-- Use localStudent instead of student -->
    <el-form :model="localStudent" label-position="top">

      <el-form-item :label="localStudent.sid ? 'Student ID' : localStudent.tid ? 'Tutor ID' : 'Admin ID'">
        <el-input
            :value="localStudent.sid || localStudent.tid || localStudent.adminid"
            disabled
            readonly>
        </el-input>
      </el-form-item>

      <el-form-item label="First Name">
        <el-input v-model="localStudent.firstName" ></el-input>
      </el-form-item>
      <el-form-item label="Middle Name">
        <el-input v-model="localStudent.middleName" ></el-input>
      </el-form-item>
      <el-form-item label="Last Name">
        <el-input v-model="localStudent.lastName" ></el-input>
      </el-form-item>
      <el-form-item label="Email">
        <el-input v-model="localStudent.email" ></el-input>
      </el-form-item>
      <el-form-item label="Password">
        <el-input v-model="localStudent.password" type="text" ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="submitForm">Submit</el-button>
        <el-button @click="closeDrawer">Cancel</el-button>
        <el-button v-if="localStudent.adminid" color="red" @click="deleteAdmin">Delete</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>

<script lang="ts">
import { defineComponent, PropType, ref, toRefs, watch } from 'vue';
import { ElMessageBox } from 'element-plus';
import { StuACItem } from '@/components/stuMngCard.vue';
import common from '@/api/commonAPI';

export default defineComponent({
  name: 'StuMDdraw',

  props: {
    visible: Boolean,
    student: {
      type: Object as PropType<StuACItem>,
      required: true,
    },
  },

  setup(props, { emit }) {
    const { student } = toRefs(props); // Convert props to reactive references

    // Create a local copy of the student prop
    const localStudent = ref({ ...student.value });

    watch(student, (newVal) => {
      Object.assign(localStudent.value, newVal);
    });

    const closeDrawer = () => {
      emit('update:visible', false);
    }

    const submitForm = async () => {
      try {
        await ElMessageBox.confirm(
            `Are you sure you want to edit this appointment?`,
            'Confirmation',
            {
              confirmButtonText: 'OK',
              cancelButtonText: 'Cancel',
              type: 'warning'
            }
        );

        // Print content of localStudent.value
        console.log("localStudent.value:", localStudent.value);

        let apiEndpoint;

        if (localStudent.value.sid) {
          apiEndpoint = '/api/student/updateStudent';
        } else if (localStudent.value.tid) {
          apiEndpoint = '/api/tutor/updateTutor';
        } else if (localStudent.value.adminid) {
          apiEndpoint = '/api/admin/updateAdmin';
        }

        if (localStudent.value.sid) {
          localStudent.value.uid = localStudent.value.sid;
          delete localStudent.value.sid;
        } else if (localStudent.value.tid) {
          localStudent.value.uid = localStudent.value.tid;
          delete localStudent.value.tid;
        } else if (localStudent.value.adminid) {
          localStudent.value.uid = localStudent.value.adminid;
          delete localStudent.value.adminid;
        }

        const response = await common(apiEndpoint, localStudent.value, 'POST');

        // Print content of response
        console.log("API response:", response);

        if (response.data.code !== 1) {
          console.error("API call was not successful");
        } else {
          // Handle success, e.g., give user feedback
          closeDrawer();
          location.reload();
        }
      } catch (error) {
        console.error("Error submitting form:", error);
      }
    };

    const deleteAdmin = async () => {
      try {

        const response = await common(
            '/api/admin/deleteAdmin',
            {
              uid: localStudent.value.adminid,
            },
            "POST",
        );

        console.log(response);
      } catch (error) {
        console.error("Error registering admin:", error);
      }
    };

    return {
      localStudent,
      closeDrawer,
      submitForm,
      deleteAdmin,
    }
  }
});
</script>
