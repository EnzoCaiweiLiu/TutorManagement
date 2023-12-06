<template>
    <div>
      <img class="wave" src="../assets/wave.svg">
      <div class="container">
        <div class="img">
          <img src="../assets/img.svg">
        </div>
        <div class="wave h">
          <h1>5Tigers Tutor Management System</h1>
        </div>
        <div class="login-content">
          <form action="login.html">
            <img src="../assets/auth.svg">
            <h2 class="title">Welcome!</h2>
            <div class="input-div one">
                <div class="i">
                    <i class="fa fa-user"></i>
                </div>
                <div class="div">
                    <h5></h5>
                    <select class="input" name="user-type" v-model="userType">
                        <option value="student">Student</option>
                        <option value="tutor">Tutor</option>
                        <option value="admin">Admin</option>
                    </select>
                </div>
            </div>
            <div class="input-div one" ref="emailInputDiv">
              <div class="i">
                <i class="fa fa-user"></i>
              </div>
              <div class="div">
                <h5>Email</h5>
                <input type="text" class="input" v-model="email" @focus="addcl('email')" @blur="remcl('email')" ref="usernameInput">
              </div>
            </div>
            <div class="input-div pass" ref="passwordInputDiv">
              <div class="i">
                <i class="fa fa-lock"></i>
              </div>
              <div class="div">
                <h5>Password</h5>
                <input type="password" class="input" v-model="password" @focus="addcl('password')" @blur="remcl('password')" ref="passwordInput">
                <span class="toggle-password" @click="togglePasswordVisibility">
                    <i class="fa fa-eye" v-if="!passwordVisible"></i>
                    <i class="fa fa-eye-slash" v-else></i>
                </span>
              </div>
            </div>
            <a href="forgetpw">Forgot Password?</a>
            <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
            </div>
            <input type="submit" class="btn" value="Login" @click="handleLogin">
            <p>Do not have an account? &nbsp; <a href="register">Sign up!</a></p>
          </form>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import 'font-awesome/css/font-awesome.min.css';
  import common from '../api/commonAPI';

  export default {
    name: 'LoginPage',
    data() {
        return {
            showPassword: false,
            email: '',
            password: '',
            errorMessage: '',
            userType: 'student'
        };
    },
    methods: {
      addcl(inputType) {
        let parent;
        if (inputType === 'email') {
          parent = this.$refs.emailInputDiv;
          parent.classList.add("focus");
        } else if (inputType === 'password') {
          parent = this.$refs.passwordInputDiv;
          parent.classList.add("focus");
        }
      },
      remcl(inputType) {
        let parent;
        if (inputType === 'email') {
          parent = this.$refs.emailInputDiv;
          if (this.$refs.usernameInput && this.$refs.usernameInput.value === "") {
            parent.classList.remove("focus");
          }
        } else if (inputType === 'password') {
          parent = this.$refs.passwordInputDiv;
          if (this.$refs.passwordInput && this.$refs.passwordInput.value === "") {
            parent.classList.remove("focus");
          }
        }
      },
        togglePasswordVisibility() {
            this.showPassword = !this.showPassword;
            const passwordInput = this.$refs.passwordInput;
            passwordInput.type = this.showPassword ? 'text' : 'password';
        },
      handleLogin(event) {
        event.preventDefault();

        let postData = {
          email: this.email,
          password: this.password
        };

        let endpoint = ''; //Initialize endpoint variables

        if (this.userType === 'student') {
          endpoint = '/api/student/studentLogin'; //Use the student login endpoint
        } else if (this.userType === 'tutor') {
          endpoint = '/api/tutor/tutorLogin'; //Use the tutor login endpoint
        } else if (this.userType === 'admin') {
          endpoint = '/api/admin/adminLogin'; //Use the administrator login endpoint
        }
        console.log("User Type:", this.userType);

        common(endpoint, postData, "POST")
            .then(res => {
              console.log(res);
              if (res.data.code === 1) {
                const userId = res.data.data[0].uid;
                localStorage.setItem("uid", userId);
                console.log(userId);
                console.log('登录成功');
                if (this.userType === 'student') {
                  this.$router.push(`/dashboard/student/${userId}`);
                } else if (this.userType === 'tutor') {
                  this.$router.push(`/dashboard/tutor/${userId}`);
                } else if (this.userType === 'admin') {
                  this.$router.push(`/dashboard/admin/${userId}`);
                }

              } else {
                this.errorMessage = "Authentication failed. Please check your username and password";
              }
            });
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

body {
  font-family: 'Poppins', sans-serif ;
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

.wave h1 {
    font-family: 'Poppins', sans-serif;
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
    margin: 1rem 0;
    cursor: pointer;
    transition: .5s;
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
</style>
