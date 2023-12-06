<template>
  <div class="edit">
    <!-- Headshot & Name Section -->
    <div class="flex-row">
      <!-- Avatar Section -->
      <div class="avatar-space" @click="triggerFileInput">
        <img v-if="photoUrl" :src="photoUrl" alt="User Photo" class="avatar-img" />
        <div v-else class="avatar-placeholder">+</div>
        <input type="file" @change="handleFileChange" ref="fileInput" class="hidden-file-input" />
      </div>

      <!-- Name Section -->
      <div class="field horizontal-fields">
        <div class="name-field">
          <h2>First Name</h2>
          <div class="input-container">
            <input v-model="editInfo.firstName" type="text" class="name-input-field">
          </div>
        </div>
        <div class="name-field">
          <h2>Middle Name</h2>
          <div class="input-container">
            <input v-model="editInfo.middleName" type="text" class="name-input-field">
          </div>
        </div>
        <div class="name-field">
          <h2>Last Name</h2>
          <div class="input-container">
            <input v-model="editInfo.lastName" type="text" class="name-input-field">
          </div>
        </div>
      </div>
    </div>

    <!-- Gender, Date of Birth & Other Field -->
    <div class="flex-row">
      <!-- Gender -->
      <div class="flex-item">
        <h2>Gender</h2>
        <select v-model="editInfo.gender" class="input-field">
          <option value="Male">Male</option>
          <option value="Female">Female</option>
          <option value="Non-Binary">Non-Binary</option>
          <option value="Prefer Not to Say">Prefer Not to Say</option>
          <option value="Other">Other</option>
        </select>
      </div>

      <!-- Date of Birth -->
      <div class="flex-item">
        <h2>Date of Birth</h2>
        <input v-model="editInfo.dob" type="date" class="input-field">
      </div>

      <!-- Other Field (Assumed based on your description) -->
      <div class="flex-item">
        <h2>Mobile</h2>
        <input v-model="editInfo.mobile" type="text" class="input-field">
      </div>
    </div>

    <!-- Email, Address & Time Zone -->
    <div class="flex-row">
      <!-- Email -->
      <div class="flex-item">
        <h2>Email</h2>
        <div class="input-container">
          <input v-model="editInfo.email" type="text" class="input-field">
        </div>
      </div>

      <!-- Address -->
      <div class="flex-item">
        <h2>Address</h2>
        <div class="input-container">
          <input v-model="editInfo.address" type="text" class="input-field">
        </div>
      </div>

      <!-- Time Zone -->
      <div class="flex-item">
        <h2>Time Zone</h2>
        <div class="input-container">
          <select v-model="editInfo.timeZone" class="input-field">
            <option value="UTC-12:00">International Date Line West</option>
            <option value="UTC-11:00">Coordinated Universal Time-11</option>
            <option value="UTC-10:00">Aleutian Islands</option>
            <option value="UTC-10:00">Hawaii</option>
            <option value="UTC-09:30">Marquesas Islands</option>
            <option value="UTC-09:00">Alaska</option>
            <option value="UTC-09:00">Coordinated Universal Time-09</option>
            <option value="UTC-08:00">Baja California</option>
            <option value="UTC-08:00">Coordinated Universal Time-08</option>
            <option value="UTC-08:00">Pacific Time (US & Canada)</option>
            <option value="UTC-07:00">Arizona</option>
            <option value="UTC-07:00">Chihuahua, La Paz, Mazatlan</option>
            <option value="UTC-07:00">Mountain Time (US & Canada)</option>
            <option value="UTC-06:00">Central Time (US & Canada)</option>
            <option value="UTC-06:00">Central America</option>
            <option value="UTC-06:00">Saskatchewan</option>
            <option value="UTC-05:00">Eastern Time (US & Canada)</option>
            <option value="UTC-05:00">Indiana (East)</option>
            <option value="UTC-04:30">Caracas</option>
            <option value="UTC-04:00">Atlantic Time (Canada)</option>
            <option value="UTC-04:00">Cuiaba</option>
            <option value="UTC-04:00">Georgetown, La Paz, Manaus, San Juan</option>
            <option value="UTC-03:30">Newfoundland</option>
            <option value="UTC-03:00">Brasilia</option>
            <option value="UTC-03:00">Buenos Aires</option>
            <option value="UTC-03:00">Greenland</option>
            <option value="UTC-03:00">Montevideo</option>
            <option value="UTC-02:00">Coordinated Universal Time-02</option>
            <option value="UTC-01:00">Azores</option>
            <option value="UTC-01:00">Cape Verde Is.</option>
            <option value="UTC">Coordinated Universal Time</option>
            <option value="UTC+01:00">Central European Time</option>
            <option value="UTC+01:00">West Central Africa</option>
            <option value="UTC+02:00">Eastern European Time</option>
            <option value="UTC+02:00">South Africa Standard Time</option>
            <option value="UTC+03:00">Arab Standard Time</option>
            <option value="UTC+03:00">East Africa Time</option>
            <option value="UTC+03:30">Iran Standard Time</option>
            <option value="UTC+04:00">Arabian Standard Time</option>
            <option value="UTC+04:00">Azerbaijan Standard Time</option>
            <option value="UTC+04:30">Afghanistan Time</option>
            <option value="UTC+05:00">Pakistan Standard Time</option>
            <option value="UTC+05:30">India Standard Time</option>
            <option value="UTC+05:45">Nepal Time</option>
            <option value="UTC+06:00">Central Asia Time</option>
            <option value="UTC+06:30">Cocos Islands Time</option>
            <option value="UTC+07:00">Bangkok, Hanoi, Jakarta</option>
            <option value="UTC+08:00">China Standard Time</option>
            <option value="UTC+08:00">Singapore Standard Time</option>
            <option value="UTC+09:00">Japan Standard Time</option>
            <option value="UTC+09:30">Central Australia Time</option>
            <option value="UTC+10:00">Eastern Australia Time</option>
            <option value="UTC+11:00">Magadan Time</option>
            <option value="UTC+12:00">Fiji Time</option>
            <option value="UTC+12:00">New Zealand Time</option>
            <option value="UTC+13:00">Tonga Time</option>
          </select>
        </div>
      </div>
    </div>

    <div class="flex-row">
      <!-- Bio -->
      <div class="flex-item">
        <h2>Bio</h2>
        <div class="input-container">
          <textarea v-model="editInfo.bio" class="textarea-field"></textarea>
        </div>
      </div>

      <!-- Summary -->
      <div class="flex-item" v-if="userType === 'tutor'">
        <h2>Summary</h2>
        <div class="input-container">
          <textarea v-model="editInfo.summary" class="textarea-field"></textarea>
        </div>
      </div>
    </div>

    <div class="field" v-if="userType === 'tutor'">
      <h2>Document</h2>
      <div class="input-container">
        <input type="file" @change="handleDocumentChange" />
      </div>
    </div>

    <button class="btn" @click="submitChanges">Save Changes</button>
  </div>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits, computed } from 'vue';
import common from '../api/commonAPI';

const fileInput = ref(null);
const photoTimestamp = ref(Date.now());
const photoUrl = computed(() => {
  if (props.userId) {
    const base = (props.userType === 'student') ? `http://192.168.233.1:8082/` : `http://192.168.233.1:8081/`;
    return `${base}${props.userId}.jpg?timestamp=${photoTimestamp.value}`;
  }
  return null;
});

const props = defineProps<{
  userInfo: any;
  userType: string;
  userId: number;
}>();

const emit = defineEmits(['infoUpdated']);
const editInfo = ref({ ...props.userInfo, userId: props.userId });


function triggerFileInput() {
  fileInput.value?.click();
}

function dateStrToTimestamp(dateStr: string): number {
  return Date.parse(dateStr);
}

async function handleFileChange(event: Event) {
  const file = (event.target as HTMLInputElement).files?.[0];
  if (file) {
    const formData = new FormData();
    formData.append('multipartFile', file);

    let endpoint = '';
    if (props.userType === 'student') {
      endpoint = `api/student/upPhoto/${props.userId}`;
    } else if (props.userType === 'tutor') {
      endpoint = `api/tutor/upPhoto/${props.userId}`;
    }

    if (endpoint) {
      try {
        const response = await common(endpoint, formData, 'POST');
        if (response.data.code === 1) {
          alert('Photo uploaded successfully!');
          photoTimestamp.value = Date.now();
          console.log(response.data)
        } else {
          alert('Failed to upload photo.');
        }
      } catch (error) {
        console.error('Error uploading photo:', error);
        alert('An error occurred while uploading.');
      }
    }
  }
}
async function submitChanges() {
  console.log("Sending data to server:", editInfo.value);

  editInfo.value.uid = editInfo.value.userId;
  delete editInfo.value.userId;

  const originalDOB = editInfo.value.dob;
  editInfo.value.dob = dateStrToTimestamp(editInfo.value.dob);

  let endpoint = '';
  if (props.userType === 'student') {
    endpoint = `api/student/updateStudent`;
  } else if (props.userType === 'tutor') {
    endpoint = `api/tutor/updateTutor`;
  }

  if (endpoint) {
    try {
      const response = await common(endpoint, editInfo.value, 'POST');
      if (response.data.code === 1) {
        alert('Information updated successfully!');
        emit('infoUpdated');
        location.reload();
      } else {
        alert('Failed to update information.');
      }
    } catch (error) {
      console.error('Error updating information:', error);
      alert('An error occurred while updating.');
    }
  }
  editInfo.value.dob = originalDOB;
}

async function handleDocumentChange(event: Event) {
  const file = (event.target as HTMLInputElement).files?.[0];
  if (file) {
    const formData = new FormData();
    formData.append('multipartFile', file);
    const endpoint = `api/tutor/upBio/${props.userId}`;

    try {
      const response = await common(endpoint, formData, 'POST');
      console.log(response)
      if (response.data.code === 1) {
        alert('Document uploaded successfully!');
        if (response.data.data) {
          editInfo.value.document = response.data.data;
        }
      } else {
        alert('Failed to upload document.');
      }
    } catch (error) {
      console.error('Error uploading document:', error);
      alert('An error occurred while uploading the document.');
    }
  }
}
</script>

<style scoped>

body {
  font-family: "Roboto", sans-serif;
  background-color: #f6f8fc;
}

.edit {
  position: absolute;
  width: 78%;
  overflow-y: auto;
  max-height: 80vh;
  margin-left: 120px;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.08);
}

h2 {
  color: #4a4a4a;
  text-transform: uppercase;
  font-size: 14px;
  letter-spacing: 1px;
  margin-left: 2px;
  margin-top: 15px;
  padding-right: 20px;
  margin-bottom: 5px;
}

.field {
  display: block;
  margin-bottom: 25px;
}

/* 输入框 & 文本区域样式 */
.input-field, .textarea-field, .name-input-field {
  width: 260px;
  padding: 10px;
  border: 1px solid #e6e6e6;
  border-radius: 8px;
  margin-top: 8px;
  font-size: 14px;
  transition: all 0.3s;
  background-color: #f9f9f9;
}

.input-field:focus, .textarea-field:focus, .name-input-field:focus {
  outline: none;
  border-color: #6C63FF;
  transform: scale(1.02);
}

.textarea-field {
  height: 120px;
  line-height: 1.5;
  resize: none;
}

/* 按钮样式 */
.btn {
  background-color: #6C63FF;
  color: #ffffff;
  border: none;
  border-radius: 8px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 15px;
  transition: all 0.3s;
}

.btn:hover {
  background-color: #554fc8;
  transform: scale(1.05);
}

/* 头像样式 */
.avatar-space {
  width: 100px;
  height: 100px;
  border: 2px solid #777;
  border-radius: 50%;
  background-size: cover;
  background-position: center;
  margin-right: 50px;
  position: relative;
  cursor: pointer; /* Add cursor pointer */
  overflow: hidden;
}

.avatar-space::before {
  content: "Upload Photo";
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  padding: 8px 0;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  text-align: center;
  font-size: 12px;
  opacity: 0;
  transform: translateY(100%);
  transition: opacity 0.3s, transform 0.3s;
}

.avatar-space:hover::before {
  opacity: 1;
  transform: translateY(0);
}

.hidden-file-input {
  display: none;
}

.avatar-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 50px;
  color: #777;
  background-color: #f0f0f0;
  border-radius: 50%;
}

.avatar-img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
}

/* 布局样式 */
.flex-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.flex-item {
  flex: 1;
  margin-right: 10px;
}

.horizontal-fields {
  display: flex;
  justify-content: space-between;
}

.name-field {
  flex: 1;
  margin-right: 10px;
}
</style>