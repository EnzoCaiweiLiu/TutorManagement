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
        <el-input v-model="localStudent.firstName" disabled readonly></el-input>
      </el-form-item>
      <el-form-item label="Middle Name">
        <el-input v-model="localStudent.middleName" disabled readonly></el-input>
      </el-form-item>
      <el-form-item label="Last Name">
        <el-input v-model="localStudent.lastName" disabled readonly></el-input>
      </el-form-item>
      <el-form-item label="Email">
        <el-input v-model="localStudent.email" disabled readonly></el-input>
      </el-form-item>
      <el-form-item label="Password">
        <el-input v-model="localStudent.password" disabled type="text" readonly></el-input>
      </el-form-item>
    </el-form>
  </el-drawer>
</template>

<script lang="ts">
import { defineComponent, PropType, ref, toRefs, watch } from 'vue';
import { StuACItem } from '@/components/stuMngCard.vue';

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

    return {
      localStudent, // Use this in your template
      closeDrawer,
    }
  }
});
</script>
