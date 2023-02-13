<template>
  <div class="background">
    <Header></Header>

    <router-view> </router-view>
  </div>

</template>

<script>
import Header from "./components/header.vue";

export default {
  name: 'App',
  components: {
    Header
  }
}
</script>

<style lang="scss" scoped>
body {
  margin: 0
}

.background {
  background-color: white;
  height: 100vh;
  overflow: hidden;
  margin: 0;
  // background-image: url("./assets/background.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
}


.black-bg {
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: fixed;
  padding: 20px;
}

.white-bg {
  width: 100%;
  background: white;
  border-radius: 8px;
  padding: 20px;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

<!--<a target="_blank" href="https://icons8.com/icon/eFuahCbSACNM/memo">memo</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>-->