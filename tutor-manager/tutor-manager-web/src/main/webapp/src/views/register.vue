<template>
  <div>
    <img class="wave" src="../assets/wave.svg" />
    <div class="container">
      <div class="introduction-container">
        <img class="img" src="../assets/wel_cats.svg" />
        <h1>Welcome to 5Tigers Tutor Management System!</h1>
        <p>Join our community and start your tutoring journey today. We offer two types of accounts</p>
        <h2>Student Account</h2>
        <p>If you're a student looking for academic support, homework help, or guidance in various subjects, our student account is perfect for you. With a student account, you can browse and connect with qualified tutors who can assist you in your learning journey.</p>
        <h2>Tutor Account</h2>
        <p>If you're an experienced tutor or educator and want to share your knowledge and expertise with students, our tutor account is designed for you. Create a tutor profile, list your areas of expertise, and start offering your tutoring services to students in need.</p>
        <p>Choose the account type that best suits your needs and get started on your educational journey with 5Tigers Tutor Management System!</p>
      </div>
      <div class="register-content">
        <form @submit.prevent="submitForm">
          <h2 class="title">Register</h2>
          <div class="input-div one">
            <div class="i">
              <i class="fa fa-user"></i>
            </div>
            <div class="div">
              <h5></h5>
              <select class="input" name="user-type" v-model="userType">
                <option value="student">Student</option>
                <option value="tutor">Tutor</option>
              </select>
            </div>
          </div>
          <div class="input-div one" ref="firstNameInputDiv">
            <div class="i">
              <i class="fa fa-user"></i>
            </div>
            <div class="div">
              <h5>First Name</h5>
              <input type="text" class="input" v-model="firstName" @focus="addcl('firstName')" @blur="remcl('firstName')" ref="firstNameInput" />
            </div>
          </div>

          <!-- Middle Name Input -->
          <div class="input-div one" ref="middleNameInputDiv">
            <div class="i">
              <i class="fa fa-user"></i>
            </div>
            <div class="div">
              <h5>Middle Name</h5>
              <input type="text" class="input" v-model="middleName" @focus="addcl('middleName')" @blur="remcl('middleName')" ref="middleNameInput" />
            </div>
          </div>

          <!-- Last Name Input -->
          <div class="input-div one" ref="lastNameInputDiv">
            <div class="i">
              <i class="fa fa-user"></i>
            </div>
            <div class="div">
              <h5>Last Name</h5>
              <input type="text" class="input" v-model="lastName" @focus="addcl('lastName')" @blur="remcl('lastName')" ref="lastNameInput" />
            </div>
          </div>
          <div class="input-div one" ref="emailInputDiv">
            <div class="i">
              <i class="fa fa-envelope"></i>
            </div>
            <div class="div">
              <h5>Email</h5>
              <input type="email" class="input" v-model="email" @focus="addcl('email')" @blur="remcl('email')" ref="emailInput" />
            </div>
          </div>
          <div class="input-div one" ref="passwordInputDiv">
            <div class="i">
              <i class="fa fa-lock"></i>
            </div>
            <div class="div">
              <h5>Password</h5>
              <input type="password" class="input" v-model="password" @focus="addcl('password')" @blur="remcl('password')" ref="passwordInput" />
            </div>
          </div>
          <div class="input-div pass" ref="confirmPasswordInputDiv">
            <div class="i">
              <i class="fa fa-lock"></i>
            </div>
            <div class="div">
              <h5>Confirm Password</h5>
              <input type="password" class="input" v-model="confirmPassword" @focus="addcl('confirmPassword')" @blur="remcl('confirmPassword')" ref="confirmPasswordInput" />
            </div>
          </div>
          <input type="submit" class="btn" value="Register" @click="handleRegister" />
          <p class="register-link">Already have an account? <router-link to="/Login">Login Now!</router-link></p>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import 'font-awesome/css/font-awesome.min.css';
import common from '../api/commonAPI';

export default {
  name: 'RegisterPage',
  data() {
    return {
      userType: 'student',
      firstName: '',
      middleName: '',
      lastName: '',
      email: '',
      password: '',
      confirmPassword: '',
      showPassword: false,
      errorMessage: '',
    };
  },
  methods: {
    addcl(inputType) {
      let parent;
      switch (inputType) {
        case 'firstName':
          parent = this.$refs.firstNameInputDiv;
          break;
        case 'middleName':
          parent = this.$refs.middleNameInputDiv;
          break;
        case 'lastName':
          parent = this.$refs.lastNameInputDiv;
          break;
        case 'email':
          parent = this.$refs.emailInputDiv;
          break;
        case 'password':
          parent = this.$refs.passwordInputDiv;
          break;
        case 'confirmPassword':
          parent = this.$refs.confirmPasswordInputDiv;
          break;
        default:
          break;
      }
      if (parent) {
        parent.classList.add('focus');
      }
    },
    remcl(inputType) {
      let parent;
      let value = '';
      switch (inputType) {
        case 'firstName':
          parent = this.$refs.firstNameInputDiv;
          value = this.$refs.firstNameInput.value;
          break;
        case 'middleName':
          parent = this.$refs.middleNameInputDiv;
          value = this.$refs.middleNameInput.value;
          break;
        case 'lastName':
          parent = this.$refs.lastNameInputDiv;
          value = this.$refs.lastNameInput.value;
          break;
        case 'email':
          parent = this.$refs.emailInputDiv;
          value = this.$refs.emailInput.value;
          break;
        case 'password':
          parent = this.$refs.passwordInputDiv;
          value = this.$refs.passwordInput.value;
          break;
        case 'confirmPassword':
          parent = this.$refs.confirmPasswordInputDiv;
          value = this.$refs.confirmPasswordInput.value;
          break;
        default:
          break;
      }
      if (value === '' && parent) {
        parent.classList.remove('focus');
      }
    },
    submitForm() {
      const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%&])[A-Za-z\d@$!%&]{8,16}$/;
      const nameRegex = /^[A-Za-z]+$/;
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

      if (this.firstName === '' || this.lastName === '' || this.email === '' || this.password === '' || this.confirmPassword === '') {
        alert('Please fill in all required fields.');
        return;
      } else if (!nameRegex.test(this.firstName) || !nameRegex.test(this.lastName)) {
        alert('First Name and Last Name can only contain letters.');
        return;
      } else if (this.middleName !== '' && !nameRegex.test(this.middleName)) { // Check if middleName is provided and valid
        alert('Middle Name can only contain letters.');
        return;
      } else if (!emailRegex.test(this.email)) {
        alert('Invalid email format.');
        return;
      } else if (this.password !== this.confirmPassword) {
        alert('Password and confirm password do not match.');
        return;
      } else if (!passwordRegex.test(this.password)) {
        alert('Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character, and be 8 to 16 characters long.');
        return;
      }

      let postData = {
        firstName: this.firstName,
        middleName: this.middleName,
        lastName: this.lastName,
        email: this.email,
        password: this.password,
      };

      let endpoint = '';  //Initialize endpoint variables

      if (this.userType === 'student') {
        endpoint = '/api/student/studentRegister';  //Use the student registration endpoint
      } else if (this.userType === 'tutor') {
        endpoint = '/api/tutor/tutorRegister';  //Use the tutor registration endpoint
      }

      common(endpoint, postData, 'POST')
          .then((res) => {
            if (res.data.code === 1) {
              alert('Registration successful!');
              this.$router.push('login');
            } else if (res.data.message === 'Email already exists') {
              this.errorMessage = "The email has already been registered.";
              alert(this.errorMessage);
            } else {
              this.errorMessage = "Registration failed. Please check your inputs.";
              alert(this.errorMessage + "\nReason: " + "This account is already registered as a student or tutor!");
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

.wave {
  position: fixed;
  bottom: 0;
  left: 0;
  height: 100%;
  z-index: -1;
}

.container {
  width: 100vw;
  height: 100vh;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  justify-content: center;
  align-items: center;
}

.img {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.img img {
  width: 500px;
}

.introduction-container {
  margin: auto;
  width: 540px;
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.introduction-container img {
  margin: auto;
  height: 150px;
}

.introduction-container h1 {
  font-family: 'Poppins', sans-serif;
  margin-bottom: 10px;
}

.introduction-container h2 {
  font-family: 'Poppins', sans-serif;
}

p {
  font-family: 'Poppins', sans-serif;
  margin-top: 10px;
}

.register-content {
  font-family: 'Poppins', sans-serif;
  margin: auto;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  text-align: center;
}

.register-content form {
  width: 326px;
  margin-right: 20px;
}

.register-content h2 {
  margin: 15px 0;
  color: #333;
  text-transform: uppercase;
  font-size: 2.9rem;
}

.input-div {
  position: relative;
  display: grid;
  grid-template-columns: 7% 93%;
  margin: 25px 0;
  padding: 5px 0;
  border-bottom: 2px solid #d9d9d9;
}

.register-content .input-div.one {
  margin-top: 0;
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

.input-div div {
  position: relative;
  height: 45px;
}

.input-div div h5 {
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

.input-div.focus div h5 {
  top: -5px;
  font-size: 15px;
}

.input-div.focus .i i {
  color: #38d39f;
}

.input-div div input {
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

.register-link {
  margin-top: 15px;
  color: #999;
  font-size: 0.9rem;
}

.register-link a {
  text-decoration: none;
  color: #38d39f;
  transition: .3s;
}

.register-link a:hover {
  color: #333;
}

.container {
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
}

.introduction-container, .register-content {
  max-width: 100%;
  display: block;
  text-align: center;
}

@media screen and (max-width: 900px) {
  .introduction-container {
    display: none;
  }
  .register-content {
    display: block;
  }
}

@media screen and (max-width: 650px) {
  .wave {
    display: none;
  }
}
</style>
