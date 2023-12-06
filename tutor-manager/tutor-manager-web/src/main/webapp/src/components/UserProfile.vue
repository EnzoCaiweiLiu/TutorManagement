<template>
  <div class="profile">
    <div class="top-section">
      <div class="avatar-space">
        <img
            v-if="photoUrl"
            :src="photoUrl"
            alt="User Photo"
            class="avatar-img"
            @error="refreshAvatar"
        />
      </div>

      <div class="name-section">
        <div class="name-item">
          <h2>First Name</h2>
          <p>{{ userInfo.firstName }}</p>
        </div>
        <div class="name-item">
          <h2>Middle Name</h2>
          <p>{{ userInfo.middleName }}</p>
        </div>
        <div class="name-item">
          <h2>Last Name</h2>
          <p>{{ userInfo.lastName }}</p>
        </div>
      </div>
    </div>

    <div class="flex-row">
      <div class="flex-item">
        <h2>Gender</h2>
        <p>{{ userInfo.gender }}</p>
      </div>
      <div class="flex-item">
        <h2>Date of Birth</h2>
        <p>{{ formattedDOB }}</p>
      </div>
      <div class="flex-item">
        <h2>Address</h2>
        <p>{{ userInfo.address }}</p>
      </div>
    </div>

    <div class="flex-row">
      <div class="flex-item">
        <h2>Mobile</h2>
        <p>{{ userInfo.mobile }}</p>
      </div>
      <div class="flex-item">
        <h2>Time Zone</h2>
        <p>{{ userInfo.timeZone }}</p>
      </div>
      <div class="flex-item">
        <h2>Email</h2>
        <p>{{ userInfo.email }}</p>
      </div>
    </div>

    <h2>Bio</h2>
    <p>{{ userInfo.bio }}</p>

    <h2 v-if="userType === 'tutor'">Summary</h2>
    <p v-if="userType === 'tutor'">{{ userInfo.summary }}</p>

    <h2 v-if="userType === 'tutor'">Document</h2>
    <div v-if="userInfo && documentNames.length">
      <div v-for="documentName in documentNames" :key="documentName" class="document">
        <a href="javascript:void(0);" @click="downloadDocument(documentName)" class="document-link">{{ documentName }}</a>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { ref, computed } from 'vue';
import common from '../api/commonAPI';

export default {
  name: "UserProfile",
  props: ['userInfo', 'userType', 'userId'],
  setup(props: { userInfo: any, userType: string, userId: string }) {
    const photoTimestamp = ref(Date.now());

    const documentNames = computed(() => {
      if (props.userInfo && props.userInfo.document) {
        return props.userInfo.document.split(',');
      }
      return [];
    });

    const formattedDOB = computed(() => {
      if (props.userInfo && props.userInfo.dob) {
        const date = new Date(props.userInfo.dob);
        return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
      }
      return null;
    });

    const photoUrl = computed(() => {
      if (props.userId) {
        const base = (props.userType === 'student') ? `http://192.168.233.1:8082/` : `http://192.168.233.1:8081/`;
        return `${base}${props.userId}.jpg?timestamp=${photoTimestamp.value}`;
      }
      return null;
    });

    const refreshAvatar = () => {
      photoTimestamp.value = Date.now();
    };

    const downloadDocument = async (documentName: string) => {
      try {
        console.log(documentName)
        const response = await common(
            `api/tutor/downBio/${props.userId}`,
            `fileName=${encodeURIComponent(documentName)}`,
            'POST',
            {
              responseType: 'blob',
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
              }
            }
        );

        if (response.status === 200) {
          const blob = new Blob([response.data], { type: response.headers['content-type'] });
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = documentName;
          document.body.appendChild(a);
          a.click();
          a.remove();
          window.URL.revokeObjectURL(url);
        } else {
          console.error('Failed to download document:', response.statusText);
        }
      } catch (error) {
        console.error('Error downloading document:', error);
      }
    };

    return {
      photoUrl,
      refreshAvatar,
      downloadDocument,
      formattedDOB,
      documentNames
    };
  }
}
</script>

<style scoped>
.profile {
  position: absolute;
  width: 78%;
  overflow-y: auto;
  max-height: 80vh;
  margin-left: 120px;
  margin-top: 3%;
}

.top-section {
  display: flex;
  align-items: flex-start;
}

.avatar-space {
  width: 135px;
  height: 135px;
  border-radius: 50%;
  overflow: hidden;
  position: relative;
}

.avatar-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}

.name-section {
  flex: 70%;
  display: flex;
  justify-content: space-between;
}

.name-item {
  flex: 23%;
}

.flex-row {
  display: flex;
  justify-content: space-between;
}

.flex-item {
  flex: 1;
  margin-right: 10px;
}

h2 {
  color: #777;
  font-family: "Roboto", sans-serif;
  width: 80%;
  text-transform: uppercase;
  font-size: 16px;
  letter-spacing: 1px;
  margin-left: 2px;
}

p {
  border-width: 1px;
  border-style: solid;
  border-image: linear-gradient(to right, #6C63FF, rgba(126, 211, 134, 0.5)) 1
  0%;
  border-top: 0;
  width: 80%;
  font-family: "Montserrat", sans-serif;
  font-size: 1.2em;
  padding: 7px 0;
  color: #070707;
}

.document {
  background-color: #f6f8fa;
  padding: 10px 15px;
  margin: 10px 0;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  width: 80%;
}

.document-link {
  text-decoration: none;
  color: #333;
  font-weight: bold;
  font-size: 16px;
  transition: all 0.3s ease;
}

.document-link:hover {
  color: #007BFF;
  text-decoration: underline;
  cursor: pointer;
}

.document:hover {
  background-color: #eef1f4;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
</style>
