<template>
  <div class="delete-profile">
    <h2>Delete Account</h2>
    <div class="confirmation">
      <p>Please type "delete" to confirm account deletion:</p>
      <input v-model="confirmationText" type="text" placeholder="Type 'delete' here">
    </div>

    <button @click="deleteAccount" :disabled="!canDelete" class="btn">Delete Account</button>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, defineProps } from 'vue';
import common from '../api/commonAPI';

const props = defineProps<{
  userType: string;
  userId: number;
}>();

const confirmationText = ref('');
const canDelete = ref(false);

watch(confirmationText, (newValue) => {
  canDelete.value = newValue.toLowerCase() === 'delete';
});

async function deleteAccount() {
  let endpoint = '';
  if (props.userType === 'student') {
    endpoint = `api/student/deleteStudent`;
  } else if (props.userType === 'tutor') {
    endpoint = `api/tutor/deleteTutor`;
  }

  if (endpoint && canDelete.value) {
    try {
      const postData = {
        uid: props.userId,    // Added uid parameter
      };
      const response = await common(endpoint, postData, 'POST');  // Updated the second parameter to postData
      if (response.data.code === 1) {
        alert('Account deleted successfully!');
        // You can also redirect the user or take other actions here
        window.location.href = '/login';
      } else {
        alert('Failed to delete account.');
      }
    } catch (error) {
      console.error('Error deleting account:', error);
      alert('An error occurred while deleting the account.');
    }
  }
}
</script>

<style scoped>
.delete-profile {
  margin-top: 180px;
  padding: 20px;
}

.confirmation {
  margin-bottom: 20px;
}

input {
  height: 20px;
  width: 240px;
}

.btn {
  background-color: red;
  color: white;
  padding: 10px 15px;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn:disabled {
  background-color: #aaa;
  cursor: not-allowed;
}
</style>