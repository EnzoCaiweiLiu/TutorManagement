<template>
  <div>
    <div id="logo" class="logo">
      <h1 class="logo">5Tigers</h1>
    </div>
    <div class="container">


      <div class="leftbox">
        <nav class="nav">
          <a href="#" @click.prevent="showProfile">
            <i class="fa fa-user"></i><span class="nav-text">Profile</span>
          </a>
          <a href="#" @click.prevent="showEdit">
            <i class="fa fa-tasks"></i><span class="nav-text">Edit</span>
          </a>
          <a @click="goToDashboard" href="#">
            <i class="fa fa-tv"></i><span class="nav-text">Dashboard</span>
          </a>
          <a href="#" @click.prevent="showDelete">
            <i class="fa fa-cog"></i><span class="nav-text">Delete</span>
          </a>
        </nav>
      </div>

      <div class="rightbox">
        <UserProfile
            v-if="activeTab === 'profile'"
            :userInfo="userInfo"
            :userType="userType"
            :userId="userId" />
        <EditProfile
            v-else-if="activeTab === 'edit'"
            :userInfo="userInfo"
            :userType="userType"
            :userId="userId" />
        <DeleteProfile
            v-else-if="activeTab === 'delete'"
            :userType="userType"
            :userId="userId" />
      </div>


    </div>

    <footer>
      5 Tigers &copy; 2023
    </footer>
  </div>
</template>

<script>
import 'font-awesome/css/font-awesome.min.css';
import common from '../api/commonAPI';
import UserProfile from '../components/UserProfile.vue';
import EditProfile from "@/components/EditProfile.vue";
import DeleteProfile from "@/components/DeleteProfile.vue";

export default {
  name: "AccountPage",
  components: {
    UserProfile,
    EditProfile,
    DeleteProfile
  },
  props: ['userType', 'userId'],
  data() {
    return {
      userInfo: {
        firstName: '',
        middleName: '',
        lastName: '',
        gender: '',
        dob: '',
        email: '',
        mobile: '',
        photoUrl: '',
        address: '',
        timeZone: '',
        hide: '',
        isDelete: false,
        bio: '',
        summary: '',
        document: '',
      },
      activeTab: 'profile',
    }
  },

  methods: {
    showProfile() {
      this.activeTab = 'profile';
    },
    showEdit() {
      this.activeTab = 'edit';
    },
    showDelete() {
      this.activeTab = 'delete';
    },
    goToDashboard() {
      this.$router.push({
        name: 'dashboard',
        params: {
          userId: this.userId,
          userType: this.userType
        }
      });
    }
  },


  created() {
    const uid = this.userId;

    if (uid) {
      let endpoint = '';

      if (this.userType === 'student') {
        endpoint = `/api/student/queryStudentById`;
      } else if (this.userType === 'tutor') {
        endpoint = `/api/tutor/queryTutorById`;
      }

      if (endpoint) {
        common(endpoint, {uid: uid}, 'POST')
            .then(res => {
              if (res.data.code === 1) {
                let userData;

                // 判断res.data.data是否为数组
                if (Array.isArray(res.data.data)) {
                  if (res.data.data.length > 0) {
                    userData = res.data.data[0];
                  }
                } else {
                  userData = res.data.data; // When res.data.data is an object
                }

                if (userData) {
                this.userInfo = {
                  firstName: userData.firstName,
                  middleName: userData.middleName,
                  lastName: userData.lastName,
                  gender: userData.gender,
                  dob: new Date(userData.dob).toLocaleString(),
                  email: userData.email,
                  mobile: userData.mobile,
                  photoUrl: userData.photoUrl,
                  address: userData.address,
                  timeZone: userData.timeZone,
                  hide: userData.hide,
                  isDelete: userData.isDelete,
                  bio: userData.bio || '', // If bio is null, set to empty string
                  summary: userData.summary || '', // If summary is null, set it to an empty string
                  document: userData.document || ''// If document is null, set to empty string
                };
                } else {
                  alert('Error getting user details');
                }
              } else {
                alert('Error getting user details');
              }
            })
      }
    }
  }
}
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Montserrat:wght@400;900&family=Nunito:wght@400;900&family=Roboto:wght@400;900&display=swap");

html, body {
  overflow: hidden;
  box-sizing: border-box;
  height: 100%;
  margin: 0;
  padding: 0;
  background-color: #3fb6a8;
}

.container {
  width: 95%;
  height: 680px;
  display: flex;
  justify-content: center;
  position: relative;
  margin-top: 7%;
  box-shadow: 2px 5px 20px rgba(119, 119, 119, 0.5);
  overflow: visible;
}

.logo {
  float: left;
  margin-left: 120px;
  margin-top: 0px;
  font-family: "Nunito Sans", sans-serif;
  color: #6C63FF;
  font-weight: 900;
  font-size: 1.5rem;
  letter-spacing: 1px;
}

.logo h1{
  font-family: "Montserrat", sans-serif;
  color: #6C63FF;
  font-size: 2em;
  margin-top: 10px;
  margin-right: 110px;
  margin-bottom: 30px;
}

.leftbox {
  float: left;
  top: -5%;
  left: 5%;
  position: absolute;
  width: 10%;
  height: 100%;
  background: #6C63FF;
  box-shadow: 3px 3px 10px rgba(119, 119, 119, 0.5);
  display: grid;
  place-items: center;
}

.nav {
  padding: 0;
}

nav a {
  list-style: none;
  padding-bottom: 45px;
  color: #fff;
  font-size: 1.5em;
  display: block;
  transition: all 0.3s ease-in-out;
  align-items: center;
  text-decoration: none;
}

nav a:hover {
  color: #3fb6a8;
  transform: scale(1.2);
  cursor: pointer;
}

nav a:first-child {
  margin-top: 7px;
}

.nav-text {
  display: grid;
  place-items: center;
  font-size: 0.6em;
  text-align: center;
  align-items: center;
  font-family: "Montserrat", sans-serif;
}

.active {
  color: #3fb6a8;
  align-items: center;
}

.rightbox {
  float: right;
  width: 80%;
  height: 100%;
}

h1 {
  font-family: "Montserrat", sans-serif;
  color: #6C63FF;
  font-size: 2em;
  margin-top: 40px;
  margin-right: 110px;
  margin-bottom: 35px;
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

footer {
  position: absolute;
  width: 20%;
  bottom: 10px;
  right: 20px;
  text-align: right;
  font-size: 0.8em;
  text-transform: uppercase;
  letter-spacing: 2px;
  font-family: "Roboto", sans-serif;
}

footer p {
  border: none;
  padding: 0;
}

footer a {
  color: #fff;
  text-decoration: none;
}

footer a:hover {
  color: #7d7d7d;
}

@media screen and (max-width: 760px) {
  .nav-text {
    display: none;
  }
}
</style>
