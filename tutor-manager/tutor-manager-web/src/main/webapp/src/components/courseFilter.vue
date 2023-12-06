<template>
  <div class="filter-header">
    <span>Course List</span>
    <div class="mt-4">
      <el-input
          v-model="input1"
          placeholder="Please input"
          class="input-with-select"
      >
        <template #prepend>
          <el-select placeholder="Select" style="width: 115px">
            <el-option label="comp" value="1" />
            <el-option label="gsoe" value="2" />
            <el-option label="math" value="3" />
          </el-select>
        </template>
        <template #append>
          <el-button>
            <!-- Your SVG icon for the button -->
          </el-button>
        </template>
      </el-input>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'CourseFilter',
  props: {
    modelValue: String
  },
  setup(props, { emit }) {
    const input1 = ref(props.modelValue);

    const updateValue = () => {
      emit('update:modelValue', input1.value);
    }

    return {
      input1,
      updateValue
    };
  }
});
</script>

<style scoped>
.filter-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
