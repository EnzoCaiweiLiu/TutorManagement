<template>
  <div class="main">
    <div class="navbar">

      <div class="icon">
        <h2 class="logo">5Tigers</h2>
      </div>
      <div class="menu">
        <ul>
          <li><a href="About">ABOUT</a></li>
          <li><a href="Login">LOGIN</a></li>
          <li><a href="Register">REGISTER</a></li>
          <li><a href="Support">SUPPORT</a></li>
        </ul>
      </div>
    </div>
    <div class="content">
      <h1>5 Tigers <br><span>Tutor Management</span> <br>System</h1>
      <p class="par">Welcome to the Tutor Management System, your one-stop solution for efficient<br>
         and effective tutoring coordination. Our platform is designed to streamline the process<br>
         of connecting students with experienced tutors, ensuring a seamless learning experience.<br>
         Whether you're a student looking for academic support or a tutor seeking opportunities<br>
         to share your expertise, our user-friendly system is here to empower you.<br>
          Explore our features, find the perfect match, <br>
         and embark on a journey of educational excellence with us!</p>

      <button class="cn"><a href="register">JOIN US</a></button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HomePage'
}
</script>

<style scoped>

@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300&display=swap');

*{
  margin: 0;
  padding: 0;
}

.main{
  width: 100%;
  background: linear-gradient(to top, rgba(0,0,0,0.5)50%,rgba(0,0,0,0.5)50%),  url(../assets/wall.jpg);
  background-position: center;
  background-size: cover;
  height: 100vh;
}

.navbar{
  width: 1200px;
  height: 75px;
  margin: auto;
}

.icon{
  width: 200px;
  float: left;
  height: 70px;
}

.logo{
  color: #6C63FF;
  font-size: 45px;
  font-family: 'Poppins', sans-serif;
  padding-left: 20px;
  float: left;
  padding-top: 10px;
  margin-top: 5px;
  margin-right: 30px;
}

.menu {
  width: 400px;
  float: left;
  height: 70px;
  margin-left: 60px;
}

ul{
  text-align: center;
  float: left;
  display: flex;
  justify-content: center;
  align-items: center;
}

ul li{
  list-style: none;
  margin-left: 92px;
  margin-top: 27px;
  font-size: 18px;
}

ul li a{
  text-decoration: none;
  color: #fff;
  font-family: 'Poppins', sans-serif;
  font-weight: bold;
  transition: 0.4s ease-in-out;
}

ul li a:hover{
  color: #6C63FF;
}

.content{
  width: 1200px;
  height: auto;
  margin: auto;
  color: #fff;
  position: relative;
}

.content .par{
  padding-left: 20px;
  padding-bottom: 25px;
  font-family: 'Poppins', sans-serif ;
  letter-spacing: 1.2px;
  line-height: 30px;
  font-size: 20px;
}

.content h1{
  font-family: 'Times New Roman';
  font-size: 50px;
  margin-top: 6%;
  margin-bottom: 30px;
  letter-spacing: 2px;
}

.content .cn{
  width: 160px;
  height: 40px;
  background: #6C63FF;
  border: none;
  margin-bottom: 10px;
  margin-top: 80px;
  font-size: 18px;
  border-radius: 10px;
  cursor: pointer;
  transition: .4s ease;

}

.content .cn a{
  text-decoration: none;
  color: #000;
  transition: .3s ease;
}

.cn:hover{
  background-color: #fff;
}

.content span{
  color: #6C63FF;
  font-size: 65px
}

.form h2{
  width: 220px;
  font-family: sans-serif;
  text-align: center;
  color: #6C63FF;
  font-size: 22px;
  background-color: #fff;
  border-radius: 10px;
  margin: 2px;
  padding: 8px;
}

.form input{
  width: 240px;
  height: 35px;
  background: transparent;
  border-bottom: 1px solid #6C63FF;
  border-top: none;
  border-right: none;
  border-left: none;
  color: #fff;
  font-size: 15px;
  letter-spacing: 1px;
  margin-top: 30px;
  font-family: sans-serif;
}

.form input:focus{
  outline: none;
}

::placeholder{
  color: #fff;
  font-family: Arial;
}

.btn a{
  text-decoration: none;
  color: #000;
  font-weight: bold;
}
.form .link{
  font-family: Arial, Helvetica, sans-serif;
  font-size: 17px;
  padding-top: 20px;
  text-align: center;
}
.form .link a{
  text-decoration: none;
  color: #6C63FF;
}
.liw{
  padding-top: 15px;
  padding-bottom: 10px;
  text-align: center;
}
.icons a{
  text-decoration: none;
  color: #fff;
}
.icons ion-icon{
  color: #fff;
  font-size: 30px;
  padding-left: 14px;
  padding-top: 5px;
  transition: 0.3s ease;
}
.icons ion-icon:hover{
  color: #6C63FF;
}

@media only screen and (max-width: 1200px) {
  /* Adapt to styles with width less than 1200px */
  .navbar, .content {
    width: 100%;
    padding: 0 20px;
  }
}
.hamburger {
  display: none;
}

@media only screen and (max-width: 768px) {
  .content .par {
    display: none;
  }
  .navbar {
    display: flex;
    justify-content: space-between; /* Align content on both ends */
    flex-wrap: wrap; /* Allow content to wrap when the content exceeds the width of the container */
  }
  .icon, .menu ul{
    flex: 1;
    min-width: 200px;
    margin-right: 100px;
  }
  .menu ul {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: 20px; /* Add spacing between li items */
  }
  .content h1 {
    margin-top: 60%;
  }
  .content h1 span{
    font-size: 46px;
  }
}
</style>