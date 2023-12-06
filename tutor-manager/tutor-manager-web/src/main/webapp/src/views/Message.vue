<template>
  <el-container>
    <div class="leftbar">
      <div class="user-profile">
        <div class="avatar-space">
          <img
              v-if="photoUrl"
              :src="photoUrl"
              alt="User Photo"
              class="avatar-img"
          />
        </div>
        <h3>{{ fullName }}</h3>
        <p>{{ userType }}</p>
        <p>{{ email }}</p>
      </div>
      <btn @click="goToDashboard" href="#" class="profile-btn">Back to Dashboard</btn>
    </div>

    <el-aside width="20%" element-loading-background="#" class="midbar">
      <el-button class="new" @click="showDrawer">New Message</el-button>
      <el-menu :default-active="activeConversationId" class="el-menu-vertical-demo" @select="selectConversation">
        <el-menu-item v-for="conversation in uniqueConversations" :key="conversation.id" :index="conversation.id.toString()" class="menuItem">
          <el-avatar :src="conversation.avatar"></el-avatar>
          {{ conversation.name }}
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- Message Area -->
    <el-main class="message-container">
      <div v-if="activeConversationId">
        <el-row>
          <el-col :span="24">
            <div class="message-box">
              <div v-for="message in activeMessages" :key="message.mid" class="message" :class="{ 'from-user': message.isFromCurrentUser, 'from-contact': !message.isFromCurrentUser }">
                <div class="message-content">
                  {{ message.content }}
                </div>
                <div class="message-time" :class="{ 'right-align': message.isFromCurrentUser, 'left-align': !message.isFromCurrentUser, 'time-from-contact': !message.isFromCurrentUser }">
                  {{ formatTime(message.time) }}
                </div>
              </div>
            </div>
          </el-col>
        </el-row>

        <el-row class="send-message-container">
          <el-col :span="24" class="send-message-column">
            <el-input class="input" type="textarea" v-model="messageContent"></el-input>
            <el-button class="sendbtn" @click="sendMessage">Send</el-button>
          </el-col>
        </el-row>
      </div>

      <div v-else class="no-contact-selected">
        <p>Please select a contact to start a conversation.</p>
      </div>
    </el-main>

    <el-drawer
        v-model="drawer"
        title="Send New Message"
        :with-header="false"
        size="40%"
    >
      <div>
        <el-form>
          <el-form-item label="Tutor ID:">
            <el-input v-model="tutorId" :disabled="userType === 'tutor'"></el-input>
          </el-form-item>
          <el-form-item label="Student ID:">
            <el-input v-model="studentId" :disabled="userType === 'student'"></el-input>
          </el-form-item>
          <el-form-item label="Message:">
            <el-input type="textarea" v-model="newMessageContent"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="sendNewMessage">Send</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-drawer>
  </el-container>
</template>

<script>
import {ref, onMounted, computed} from 'vue';
import common from '@/api/commonAPI';
import { useRouter } from 'vue-router';
export default {
  name: "MessagePage",
  props: {
    userType: String,
    userId: Number,
  },

  setup(props) {
    const fullName = ref('');
    const email = ref('');
    const conversations = ref([]);
    const activeConversationId = ref(null);
    const activeMessages = ref([]);
    const newMessage = ref('');
    const photoTimestamp = ref(Date.now());
    const router = useRouter();
    const drawer = ref(false);
    const tutorId = ref('');
    const studentId = ref('');
    const messageContent = ref('');
    const newMessageContent = ref('');
    const showDrawer = () => {
      drawer.value = true;
    };

    const uniqueConversations = computed(() => {
      const unique = {};
      conversations.value.forEach(convo => {
        unique[convo.id] = convo;
      });
      return Object.values(unique);
    });

    const sendNewMessage = async () => {
      if (newMessageContent.value.trim() === '') {
        alert('Message cannot be empty!');
        return;
      }

      const payload = {
        message: newMessageContent.value,
        tToS: props.userType === 'tutor' ? 1 : 0,
        time: new Date().toISOString()
      };

      if (props.userType === 'student') {
        payload.sid = props.userId;
        payload.tid = tutorId.value;
      } else if (props.userType === 'tutor') {
        payload.tid = props.userId;
        payload.sid = studentId.value;
      }

      try {
        console.log(payload)
        const response = await common('api/message/addMessage', payload, 'POST');
        if (response.data.code === 1) {
          //Message sent successfully
          console.log('Message sent successfully');
          // Close the drawer
          drawer.value = false;
          location.reload();
          // Clear the input box
          tutorId.value = '';
          studentId.value = '';
          messageContent.value = '';
        } else {
          console.error('Error sending message:', response.data.message);
        }
      } catch (error) {
        console.error('Error sending message:', error);
      }
    };

    const getUserInfo = async () => {
      let endpoint = '';
      if (props.userType === 'admin') {
        endpoint = 'api/admin/updateAdmin';
      } else if (props.userType === 'student') {
        endpoint = 'api/student/queryStudentById';
      } else if (props.userType === 'tutor') {
        endpoint = 'api/tutor/queryTutorById';
      }

      try {
        const response = await common(endpoint, { uid: props.userId }, 'POST');
        if (response.data.code === 1) {
          const userInfo = props.userType === 'tutor' ? response.data.data[0] : response.data.data;
          fullName.value = [userInfo.firstName, userInfo.middleName, userInfo.lastName].filter(Boolean).join(' ');
          email.value = userInfo.email;
        } else {
          console.error('Error fetching user info:', response.data.message);
        }
      } catch (error) {
        console.error('Error fetching user info:', error);
      }
    };

    const photoUrl = computed(() => {
      if (props.userId) {
        const base = (props.userType === 'student') ? `http://192.168.233.1:8082/` : `http://192.168.233.1:8081/`;
        return `${base}${props.userId}.jpg?timestamp=${photoTimestamp.value}`;
      }
      return null;
    });

    async function getContactInfo(userId, userType) {
      let endpoint = '';
      let postData = {};

      if (userType === 'tutor') {
        endpoint = 'api/tutor/queryTutorById';
        postData.uid = userId;
      } else if (userType === 'student') {
        endpoint = 'api/student/queryStudentById';
        postData.uid = userId;
      }

      try {
        const response = await common(endpoint, postData, 'POST');
        if (response.data.code === 1) {
          const userInfo = props.userType === 'student' ? response.data.data[0] : response.data.data;
          const fullName = [userInfo.firstName, userInfo.middleName, userInfo.lastName].filter(Boolean).join(' ');

          return { name: fullName };
        } else {
          console.error('Error fetching contact info:', response.data.message);
          return { name: 'Unknown' };
        }
      } catch (error) {
        console.error('Error fetching contact info:', error);
        return { name: 'Unknown' };
      }
    }

    const fetchConversations = async () => {
      try {
        const response = await common('api/message/findMessageBySelective', {
          [props.userType === 'student' ? 'sid' : 'tid']: props.userId
        }, 'POST');

        if (response.data.code === 1) {
          // Assume that the returned data structure is { data: [ {tid: number, sid: number, ...}, ... ] }
          conversations.value = await Promise.all(response.data.data.map(async msg => {
            // Determine who the current user is talking to
            const contactType = props.userType === 'student' ? 'tutor' : 'student';
            const contactId = contactType === 'student' ? msg.sid : msg.tid;
            const { name } = await getContactInfo(contactId, contactType);

            return {
              id: contactId,
              name,
            };
          }));
        } else {
          console.error('Error fetching conversations:', response.data.message);
        }
      } catch (error) {
        console.error('Error fetching conversations:', error);
      }
    };

    const formatTime = (time) => {
      const date = new Date(time);
      return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
    };

    const selectConversation = async (contactId) => {
      console.log('Selected conversation:', contactId);
      activeConversationId.value = contactId;
      const userTypeKey = props.userType === 'student' ? 'sid' : 'tid';
      const contactTypeKey = props.userType === 'student' ? 'tid' : 'sid';

      try {
        const response = await common('api/message/findMessageBySelective', {
          [userTypeKey]: props.userId,
          [contactTypeKey]: contactId
        }, 'POST');

        if (response.data.code === 1) {
          activeMessages.value = response.data.data.map(msg => ({
            ...msg,
            isFromCurrentUser: (props.userType === 'tutor' && msg.tToS) || (props.userType === 'student' && !msg.tToS),
            content: msg.message,
            time: msg.time,
          }));
        } else {
          console.error('Error fetching messages:', response.data.message);
        }
      } catch (error) {
        console.error('Error fetching messages:', error);
      }
    };

    const sendMessage = async () => {
      if (messageContent.value.trim() === '') {
        alert('Message cannot be empty!');
        return;
      }

      const payload = {
        message: messageContent.value,
        tToS: props.userType === 'tutor' ? 1 : 0,
        time: new Date().toISOString()
      };

      if (props.userType === 'student') {
        payload.sid = props.userId;
        payload.tid = activeConversationId.value; // Use the selected contact ID
      } else if (props.userType === 'tutor') {
        payload.tid = props.userId;
        payload.sid = activeConversationId.value; // Use the selected contact ID
      }

      try {
        console.log(payload)
        const response = await common('api/message/addMessage', payload, 'POST');
        if (response.data.code === 1) {
          //Message sent successfully
          console.log('Message sent successfully');
          localStorage.setItem('lastActiveContact', activeConversationId.value);
          location.reload();
        } else {
          console.error('Error sending message:', response.data.message);
        }
      } catch (error) {
        console.error('Error sending message:', error);
      }
    };

    onMounted(() => {
      if (props.userType && props.userId) {
        getUserInfo();
        fetchConversations().then(() => {
          const lastActiveContact = localStorage.getItem('lastActiveContact');
          if (lastActiveContact) {
            selectConversation(lastActiveContact);
            localStorage.removeItem('lastActiveContact'); // Clear after selection
          }
        });
      }
    });

    const goToDashboard = () => {
      router.push({
        name: 'dashboard',
        params: {
          userId: props.userId,
          userType: props.userType
        }
      });
    };

    return {
      fullName,
      email,
      photoUrl,
      conversations,
      activeConversationId,
      activeMessages,
      newMessage,
      drawer,
      tutorId,
      studentId,
      messageContent,
      uniqueConversations,
      newMessageContent,
      formatTime,
      goToDashboard,
      showDrawer,
      selectConversation,
      sendMessage,
      sendNewMessage,
    };
  }
};
</script>

<style scoped>

@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');

.leftbar {
  display: flex;
  flex-direction: column;
}

.midbar {
  background-color: #99999940;
}

.user-profile {
  padding: 10px;
  border-bottom: 1px solid #ebeef5;
  font-family: 'Poppins', sans-serif;
  width: 260px;
}

.new {
  height: 50px;
  padding: 10px;
  margin-top: 20px;
  width: 80%;
  margin-bottom: 20px;
  background-color: #aaaaaa70;
}

.profile-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 40px;
  width: 80%;
  margin: 0 auto;
  margin-top: 100%;
  color: #4a4a4a;
  text-decoration: none;
  font-size: 20px;
  font-weight: bold;
  border-radius: 3%;
  font-family: 'Poppins', sans-serif;
  background-color: #99999990;
}

.profile-btn:hover {
  background-color: #32be8f;
  color: #000000;
  font-weight: bolder;
  transition: .3s;
}

.avatar-space {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  overflow: hidden;
  position: relative;
  margin: auto;
}

.avatar-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}

el-menu-item {
  max-width: 26px;
}

.message-box {
  height: calc(80vh - 80px);
  overflow-y: auto;
  padding: 20px;
}

.message {
  padding: 10px;
  border-bottom: 1px solid #ebeef5;
}

.message-container {
  height: 95vh;
}

.message-input {
  margin-bottom: 20px;
}

.sendbtn {
  display: block;
  width: 100%;
  height: 50px;
  border-radius: 25px;
  outline: none;
  border: none;
  background-image: linear-gradient(to right, #6C63FF, #4941ec, #32be8f);
  background-size: 200%;
  font-size: 1.2rem;
  color: #fff;
  font-family: 'Poppins', sans-serif;
  text-transform: uppercase;
  margin: 1rem 0;
  cursor: pointer;
  transition: .5s;
}

.menuItem {
  background-color: #99999960;
}

.menuItem:hover {
  background-color: #FFFFFF;
}

.content {
  display: inline-block;
  background-color: #f0f2f5;
  padding: 10px;
  border-radius: 4px;
}

.el-avatar {
  margin-right: 10px;
  border-color: #4a4a4a;
  border-right: 20px;
}

.from-contact {
  background-color: #557FE8;
  color:#FFFFFF;
  width: 60%;
  margin-right: auto;
  padding: 8px 12px;
  border-radius: 15px;
}

.from-user {
  background-color: #7d7d7d70;
  width: 60%;
  margin-left: auto;
  padding: 8px 12px;
  border-radius: 15px;
  margin-bottom: 10px;
}

.no-contact-selected {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  text-align: center;
  font-size: 1.2rem;
  color: #666;
}

.message-time {
  font-size: 0.8rem;
  color: #888;
  margin-top: 5px;
}

.right-align {
  text-align: right;
}

.left-align {
  text-align: left;
}

.time-from-contact {
  color: #FFF;
}
</style>
