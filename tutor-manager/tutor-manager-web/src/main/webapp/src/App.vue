<template>
  <router-view/>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  position: absolute;
  height: 100%;
  width: 100%;
  overflow: hidden;
}

html,
body {
  min-height: 100%;
  margin: 0;
  padding: 0;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}

@media screen and (max-width: 900px) {
  html {
    font-size: 14px;
  }
}
</style>


<script>
export default {
  data() {
    return {};
  },
  methods: {}
};
</script>