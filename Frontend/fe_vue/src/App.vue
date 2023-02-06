<template>
 
<div>
  <Header />
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
  text-decoration: none;
}
.menu a {
  color: white;
  padding: 10px;
  text-decoration: none;

}

</style>
