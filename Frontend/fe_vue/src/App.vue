<template>
<div class="mainLogo">
  DevRoad
</div>
  <div class="menu">
    <a>
      <router-link to="/signup">회원가입</router-link>
    </a>
  </div>
<div>
  <router-view> </router-view>
</div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      신고수: 0,
      메뉴들: ["Home", "About"],
    }
  },
  methods: {

  },
  components: {

  }
}
</script>

<style>
body{
  margin:0
}
div{
  box-sizing: border-box;
}
.black-bg{
  width: 100%; height: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: fixed; padding: 20px;
}
.white-bg{
  width: 100%; background: white;
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

.menu {
  background: darkslateblue;
  padding: 15px;
  border-radius: 5px;

}
.mainLogo{
  color: darkslateblue;
  text-align: left;
  font-size: 30px;
}
.menu a {
  color: white;
  padding: 10px;
  text-decoration: none;

}

.room_image {
  width: 100%;
}
</style>
