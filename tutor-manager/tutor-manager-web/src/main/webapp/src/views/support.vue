<template>
  <div>
    <img class="wave" src="../assets/wave.svg">
    <div class="container">
      <div class="img">
        <img src="../assets/supoort.svg">
      </div>
      <div class="wave h">
        <h1>5Tigers Support</h1>
      </div>
      <div class="support-content">
        <h2 class="title">How can we help you?</h2>
        <form @submit.prevent="submitSupportForm">
          <!-- User Type Selection -->
          <div class="input-div one">
            <div class="i">
              <i class="fa fa-user"></i>
            </div>
            <div class="div">
              <h5></h5>
              <select class="input" name="user-type" v-model="userType">
                <option value="" disabled selected hidden>Select Your User Type</option>
                <option value="newUser">New User</option>
                <option value="student">Student</option>
                <option value="tutor">Tutor</option>
                <option value="Admin">Admin</option>
              </select>
            </div>
          </div>
          <div class="input-div one" ref="emailInputDiv">
            <div class="i">
              <i class="fa fa-user"></i>
            </div>
            <div class="div">
              <h5>Email</h5>
              <input type="email" class="input" v-model="email" @focus="addcl('email')" @blur="remcl('email')">
            </div>
          </div>
          <div class="div">
            <div>
              <h5>Describe your issue</h5>
              <textarea class="simple-description" v-model="description" rows="5"></textarea>
            </div>
          </div>
          <button class="btn">Submit</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import 'font-awesome/css/font-awesome.min.css';
import common from '../api/commonAPI';

export default {
  name: 'SupportPage',
  data() {
    return {
      searchQuery: '',
      results: [],
      userType: '',
      email: '',
      description: '',
    };
  },
  methods: {

    searchHelp() {
      if (this.searchQuery === 'password') {
        this.results = [
          { id: 1, title: 'How to reset your password' },
          { id: 2, title: 'Tips for a secure password' }
        ];
      } else {
        this.results = [];
      }
    },
    addcl(inputType) {
      let parent;
      if (inputType === 'email') {
        parent = this.$refs.emailInputDiv;
      } else if (inputType === 'description') {
        parent = this.$refs.descriptionInputDiv;
      } else if (inputType === 'userType') {
        parent = this.$refs.userTypeInputDiv;
      }
      parent.classList.add("focus");
    },
    remcl(inputType) {
      let parent, inputElement;
      if (inputType === 'email') {
        parent = this.$refs.emailInputDiv;
        inputElement = parent.querySelector('input');
      } else if (inputType === 'description') {
        parent = this.$refs.descriptionInputDiv;
        inputElement = parent.querySelector('textarea');
      } else if (inputType === 'userType') {
        parent = this.$refs.userTypeInputDiv;
        inputElement = parent.querySelector('select');
      }

      if (inputElement && inputElement.value === "") {
        parent.classList.remove("focus");
      }
    },
    async submitSupportForm() {
      const formData = {
        email: this.email,
        info: this.description
      };

      try {
        const response = await common('/api/support/addSupport', formData, 'POST');
        if (response.data.code === 1) {
          // code 1 indicates success
          alert('Support request submitted successfully!');
          location.reload()
        } else {
          // 处理非成功响应
          alert('Failed to submit support request.');
        }
      } catch (error) {
        console.error('Error submitting support request:', error);
        alert('An error occurred while submitting.');
      }
    }
  }
}
</script>

<style scoped>

@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');

*{
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}

body{
  font-family: 'Poppins', sans-serif;
  overflow: hidden;
}

.wave{
  position: fixed;
  bottom: 0;
  left: 0;
  right: auto;
  height: 100%;
  z-index: -1;
}

.wave h1{
  font-family: 'Poppins', sans-serif ;
  margin-left: 200pt;
  margin-top: 20px;
  text-transform: uppercase;
  color: #333;
}

.container{
  width: 100vw;
  height: 100vh;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-gap :7rem;
  padding: 0 2rem;
}

.error-message {
  color: red;
  margin-top: 5px;
}

.img{
  display: flex;
  justify-content: flex-end;
  align-items: center;
  width: 100%;
  height: 100%;
}

.login-content{
  display: flex;
  justify-content: flex-start;
  align-items: center;
  text-align: center;
}

.img img{
  width: 500px;
}

form{
  font-family: 'Poppins', sans-serif;
  width: 360px;
}

.login-content img{
  height: 100px;
}

.login-content h2{
  margin: 15px 0;
  color: #333;
  text-transform: uppercase;
  font-size: 2.9rem;
}

.login-content .input-div{
  position: relative;
  display: grid;
  grid-template-columns: 7% 93%;
  margin: 25px 0;
  padding: 5px 0;
  border-bottom: 2px solid #d9d9d9;
}

.login-content .input-div.one{
  margin-top: 0;
}

.i{
  color: #d9d9d9;
  display: flex;
  justify-content: center;
  align-items: center;
}

.i i{
  transition: .3s;
}

.input-div > div{
  position: relative;
  height: 45px;
}

.input-div > div > h5{
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  color: #999;
  font-size: 18px;
  transition: .3s;
}

.input-div:before, .input-div:after{
  content: '';
  position: absolute;
  bottom: -2px;
  width: 0%;
  height: 2px;
  background-color: #38d39f;
  transition: .4s;
}

.input-div select.input {
  width: 100%;
  padding: 12px 20px;
  border: none;
  border-radius: 20px;
  background-color: #f1f1f1;
  color: #333;
  font-size: 16px;
  font-family: 'Poppins', sans-serif;
  appearance: none;
}

.input-div select.input option {
  background-color: #f1f1f1;
  color: #333;
  font-size: 16px;
}

.input-div:before{
  right: 50%;
}

.input-div:after{
  left: 50%;
}

.input-div.focus:before, .input-div.focus:after{
  width: 50%;
}

.input-div.focus > div > h5{
  top: -5px;
  font-size: 15px;
}

.input-div.focus > .i > i{
  color: #38d39f;
}

.input-div > div > input{
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  border: none;
  outline: none;
  background: none;
  padding: 0.5rem 0.7rem;
  font-size: 1.2rem;
  color: #555;
  font-family: 'poppins', sans-serif;
}

.input-div.pass{
  margin-bottom: 4px;
}

a{
  display: block;
  text-align: right;
  text-decoration: none;
  color: #999;
  font-size: 0.9rem;
  transition: .3s;
}

a:hover{
  color: #38d39f;
}

.btn{
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
  cursor: pointer;
  transition: .5s;
  margin-top: 10px;
}
.btn:hover{
  background-position: right;
}


@media screen and (max-width: 1050px){
  .container{
    grid-gap: 5rem;
  }
}

@media screen and (max-width: 1000px){
  form{
    width: 290px;
  }

  .login-content h2{
    font-size: 2.4rem;
    margin: 8px 0;
  }

  .img img{
    width: 400px;
  }
}

@media screen and (max-width: 900px){
  .container{
    grid-template-columns: 1fr;
  }

  .img{
    display: none;
  }

  .wave{
    display: none;
  }

  .login-content{
    justify-content: center;
  }
}

.login-content p {
  margin-top: 0;
  color: #999;
  font-size: 0.9rem;
  display: flex;
  align-items: center;
}

.login-content p a {
  text-decoration: none;
  color: #38d39f;
  transition: .3s;
  margin-left: 5px;
}

.login-content p a:hover {
  color: #555;
  text-decoration: underline;
}

.toggle-password {
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  cursor: pointer;
  z-index: 2;
}

.toggle-password i {
  color: #999;
  font-size: 18px;
}

.support-content {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  text-align: center;
  padding: 20px;
  margin-top: 100px;
}

.support-content h3 {
  margin-top: 20px;
  color: #333;
}

.support-content ul {
  list-style-type: none;
  padding: 0;
}

.support-content li {
  margin: 10px 0;
  color: #555;
  cursor: pointer;
}

.support-content li:hover {
  text-decoration: underline;
  color: #38d39f;
}

.support-content {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  text-align: center;
  padding: 20px;
}

.input-div {
  position: relative;
  display: grid;
  grid-template-columns: 7% 93%;
  margin: 25px 0;
  padding: 5px 0;
  border-bottom: 2px solid #d9d9d9;
}

.input-div .i {
  display: flex;
  align-items: center;
  justify-content: center;
}

.input-div .i i {
  font-size: 1.5rem;
  color: #d9d9d9;
}

.description-input h5 {
  align-self: flex-start;
  margin-bottom: 10px;
  color: #999;
  font-size: 18px;
}

.simple-description {
  width: 100%;
  height: 200px;
  padding: 10px;
  border: 2px solid #6C63FF;
  border-radius: 10px;
  font-size: 1rem;
  color: #333;
  font-family: 'Poppins', sans-serif;
  resize: none;
  margin-top: 20px;
  outline: none;
}

.title {
  font-family: 'Poppins', sans-serif ;
}
</style>