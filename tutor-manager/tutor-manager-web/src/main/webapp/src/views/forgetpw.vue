<template>
  <div class="container">
    <div class="introduction-container">
      <h1>Password Reset</h1>
      <p>Forgot your password? No worries! We're here to help you reset it.</p>
      <p>Enter your registered email address below, and we'll send you a verification link to reset your password.</p>
    </div>
    <div class="reset-content">
      <form @submit.prevent="submitForm">
        <h2 class="title">Reset Password</h2>
        <div class="input-div one">
          <div class="i">
            <i class="fa fa-user"></i>
          </div>
          <div class="div">
            <h5></h5>
            <select class="input" name="user-type" v-model="selectedUserType">
              <option value="student">Student</option>
              <option value="tutor">Tutor</option>
              <option value="Admin">Admin</option>
            </select>
          </div>
        </div>
        <div class="input-div" ref="emailInputDiv">
          <div class="i">
            <i class="fa fa-user"></i>
          </div>
          <div class="div">
            <h5>Email</h5>
            <input type="text" class="input" v-model="email" @focus="addcl('email')" @blur="remcl('email')" ref="emailInput">
          </div>
        </div>
        <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
        </div>
        <button type="submit" class="btn">Send Verification</button>
        <p>Remember your password? <router-link to="/Login">Login Now!</router-link></p>
      </form>
    </div>
  </div>
</template>

<script>
import 'font-awesome/css/font-awesome.min.css';
import common from '../api/commonAPI';

export default {
  name: 'ForgetPW',
  data() {
    return {
      selectedUserType: 'student',
      email: '',
      errorMessage: '',
    };
  },
  methods: {
    submitForm() {
      let endpoint = '';
      if (this.selectedUserType === 'student') {
        endpoint = '/api/student/sendEmail';
      } else if (this.selectedUserType === 'tutor') {
        endpoint = '/api/tutor/sendEmail';
      } else {
        // Handle other user types or show an error
        this.errorMessage = "Invalid user type selected!";
        return;
      }

      // Jump first
      this.navigateToVerification();

      // Then send the email
      common(endpoint, { email: this.email, userType: this.selectedUserType }, "POST")
          .then(res => {
            console.log(res);
            if (res.data.code !== 1) {
              this.errorMessage = "Email does not exist!";
            }
          })
          .catch(err => {
            this.errorMessage = "Error sending verification email!";
            console.error('Error sending verification email:', err);
          });

    },
    addcl(inputType) {
      if (inputType === 'email') {
        let parent = this.$refs.emailInputDiv;
        parent.classList.add('focus');
      }
    },
    remcl(inputType) {
      if (inputType === 'email') {
        let parent = this.$refs.emailInputDiv;
        if (this.$refs.emailInput.value === '') {
          parent.classList.remove('focus');
        }
      }
    },
    navigateToVerification() {
      this.$router.push({
        name: 'VerificationPage',
        query: {
          email: this.email,
          userType: this.selectedUserType
        }
      });
    }
  }
};
</script>

<style scoped>  
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');
* {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Poppins', sans-serif;
    overflow: hidden;
}

.error-message {
    color: red;
    margin-top: 5px;
}

.container {
  max-width: 100vw;
  height: 100vh;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  padding: 0 2rem;
  overflow-x: hidden;
}

.introduction-container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
    background-color: #e1e1e1;
    padding: 2rem;
    border-radius: 10px;
    max-height: 100vh;
    overflow-y: hidden;
}

.introduction-container h1 {
    color: #333;
    font-size: 2.4rem;
    margin-bottom: 1rem;
}

.introduction-container p {
    color: #666;
    font-size: 1rem;
}

.reset-content {
    font-family: 'Poppins', sans-serif;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
}

.reset-content form {
    width: 326px;
}

.reset-content h2.title {
    margin: 15px 0;
    color: #333;
    text-transform: uppercase;
    font-size: 2.4rem;
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
    color: #d9d9d9;
    display: flex;
    justify-content: center;
    align-items: center;
}

.input-div .i i {
    transition: .3s;
}

.input-div > div {
    position: relative;
    height: 45px;
}

.input-div > div > h5 {
    position: absolute;
    left: 10px;
    top: 50%;
    transform: translateY(-50%);
    color: #999;
    font-size: 18px;
    transition: .3s;
}

.input-div:before, .input-div:after {
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

.input-div:before {
    right: 50%;
}

.input-div:after {
    left: 50%;
}

.input-div.focus:before, .input-div.focus:after {
    width: 50%;
}

.input-div.focus > div > h5 {
    top: -5px;
    font-size: 15px;
}

.input-div.focus > .i > i {
    color: #38d39f;
}

.input-div > div > input {
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

a {
    text-align: right;
    text-decoration: none;
    color: #999;
    font-size: 0.9rem;
    transition: .3s;
}

a:hover {
    color: #38d39f;
}

.btn {
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

.btn:hover {
    background-position: right;
}

@media screen and (max-width: 900px) {
    .container {
        grid-template-columns: 1fr;
    }

    .introduction-container {
        display: none;
    }

    .reset-content {
        justify-content: center;
    }
}
</style>