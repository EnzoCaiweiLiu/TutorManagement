<template>
  <div class="container">
    <!-- nav bar -->
    <nav>
      <ul>
        <li><a href="/">Home</a></li>
      </ul>
    </nav>

    <!-- hero -->
    <div class="hero">
      <h1>˗ˏˋ About Us ´ˎ˗</h1>
      <p>
        Hello there! This is Team 5Tigers,<br>5Tigers Tutor Management System is our capstone project for Master degree in UNSW!
        <br><br>
        As of September 2023, we grouped as a team to build a web system for student and tutor.
      </p>
    </div>

    <!-- main -->
    <div class="about-me">
      <div class="hobbies">
        <h2>Front-End Developer</h2>
        <ul>
          <li>Caiwei Liu z5391857</li>
          <li>Jiaming Zou z5383972</li>
        </ul>
      </div>
      <div class="likes">
        <h2>Back-End Developer</h2>
        <ul>
          <li>Haifeng Zhang z5475362</li>
          <li>Qian Wang z5474520</li>
        </ul>
      </div>
    </div>

    <!-- copyright -->
    <footer>
      5 Tigers &copy; 2023
    </footer>
  </div>
</template>

<script>
export default {
  name: 'AboutPage',
}
</script>

<style scoped>
/* Import fonts */
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Rubik:wght@400;500&display=swap');

/* Apply the gradient background and base styles */
.container {
  font-family: 'Poppins', sans-serif;
  background: linear-gradient(135deg, #6C63FF 0%, #B8C6DB 100%);
  color: #333;
  min-height: 100vh;
  padding: 20px;
  box-sizing: border-box;
}

/* Navigation styles */
nav {
  margin-bottom: 0px;
}
nav ul {
  list-style-type: none;
  padding: 0;
  text-align: center;
}
nav ul li {
  display: inline;
}
nav ul li a {
  color: #38d39f;
  padding: 5px;
  text-decoration: none;
  font-size: 24px;
}
nav ul li a:hover {
  background-color: rgba(255,255,255,0.5);
  color: #6C63FF;
  transition: .5s;
}

/* Hero section styles */
.hero {
  text-align: center;
  padding: 30px 0;
}
.hero h1 {
  font-family: 'Rubik', sans-serif;
  color: #fff;
  text-shadow: 2px 2px 4px rgba(0,0,0,0.2);
  margin: 0;
}
.hero p {
  color: #fff;
}

/* About me section styles */
.about-me {
  text-align: center;
  margin-top: 40px;
}
.about-me h2 {
  font-family: 'Rubik', sans-serif;
  color: #fff;
  margin-bottom: 10px;
}
.about-me ul {
  list-style-type: none;
  padding: 0;
}
.about-me ul li {
  font-family: 'Poppins', sans-serif;
  color: #fff;
  margin-bottom: 5px;
}

/* Footer styles */
footer {
  text-align: center;
  color: #fff;
  margin-top: 50px;
  padding: 20px 0;
  font-size: 14px;
}

/* Add media queries and additional styling as needed... */
</style>
