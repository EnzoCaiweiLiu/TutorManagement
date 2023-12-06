<template>
    <div class="container">
      <div class="reset-content">
        <form @submit.prevent="submitForm">
          <h2 class="title">Reset Password</h2>
          <div class="input-div">
            <div class="i">
              <i class="fa fa-lock"></i>
            </div>
            <div class="div">
              <h5 class="password-label">New Password</h5>
              <input type="password" class="input" v-model="password" @input="togglePasswordLabel">
            </div>
          </div>
  
          <div class="input-div">
            <div class="i">
              <i class="fa fa-lock"></i>
            </div>
            <div class="div">
              <h5 class="confirm-password-label">Confirm Password</h5>
              <input type="password" class="input" v-model="confirmPassword" @input="toggleConfirmPasswordLabel">
            </div>
          </div>
  
          <button type="submit" class="btn">Reset Password</button>
        </form>
      </div>
    </div>
  </template>
  
  <script>
  import 'font-awesome/css/font-awesome.min.css';
  import common from '../api/commonAPI';

  export default {
    name: 'ResetPassword',
    mounted() {
      this.email = this.$route.query.email;
      this.userType = this.$route.query.userType;
    },
    data() {
      return {
        email: '',
        password: '',
        confirmPassword: '',
      };
    },
    methods: {
      togglePasswordLabel() {
        const passwordLabel = document.querySelector('.password-label');
        if (this.password) {
          passwordLabel.classList.add('active');
        } else {
          passwordLabel.classList.remove('active');
        }
      },
      toggleConfirmPasswordLabel() {
        const confirmPasswordLabel = document.querySelector('.confirm-password-label');
        if (this.confirmPassword) {
          confirmPasswordLabel.classList.add('active');
        } else {
          confirmPasswordLabel.classList.remove('active');
        }
      },
      isValidPassword(password) {
        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$/;
        return regex.test(password);
      },
      async submitForm() {
        if (this.password !== this.confirmPassword) {
          alert('Password and confirm password do not match.');
          return;
        }

        if (!this.isValidPassword(this.password)) {
          alert('Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character.');
          return;
        }

        let endpoint = '';
        console.log(this.email, this.userType)
        if (this.userType === 'student') {
          endpoint = 'api/student/updateStudentPsw';
        } else if (this.userType === 'tutor') {
          endpoint = 'api/tutor/updateTutorPsw';
        } else {
          alert('Invalid user type');
          return;
        }

        try {
          const response = await common(endpoint, { email: this.email, password: this.password }, 'POST');

          if (response.data && response.data.code === 1) {
            alert('Your password is updated. Please press OK to login!');
            console.log("API Response:", response);
            this.$router.push('/login');
          } else {
            alert('Error updating password. Please try again.');
          }
        } catch (error) {
          alert('An error occurred. Please try again later.');
          console.error('Error updating password:', error);
        }
      },
    },
  };
  </script>
  
  <style scoped>
* {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
}

.error-message {
  background-color: #ffcccc;
  color: red;
  font-size: 1rem;
  padding: 10px;
  border: 1px solid red;
  border-radius: 5px;
  margin-top: 10px;
  display: none;
  text-align: center;
}

.show-error {
  display: block;
}

body {
    font-family: 'Poppins', sans-serif;
    overflow: hidden;
}

.container {
    width: 100vw;
    height: 100vh;
    display: grid;
    grid-gap: 7rem;
    padding: 0 2rem;
}

.reset-content {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
}

.reset-content form {
    width: 360px;
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

.input-div input.input {
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
    font-family: 'Poppins', sans-serif;
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
}

.password-label,
.confirm-password-label {
    position: absolute;
    left: 10px;
    top: 50%;
    transform: translateY(-50%);
    color: #999;
    font-size: 18px;
    transition: .3s;
}

.password-label.active,
.confirm-password-label.active {
    top: -5px;
    font-size: 15px;
    color: #38d39f;
}
  </style>
  