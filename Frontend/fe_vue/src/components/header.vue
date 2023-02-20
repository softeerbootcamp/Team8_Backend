<template><!-- <div class="background"> -->
  <nav class="navbar sticky-top">
    <router-link to="/" class="mainLogo">DevRoad</router-link>
    <button class="btn" style="position:absolute;left:10%;" @click="demoOnOff">{{ DemoMode }}</button>
    <demo-btn v-if="DemoOnOff"></demo-btn>
    <div class="menu" v-if="!isAdmin">
      <a v-if="!isLogin">
        <router-link :to="{ name: 'SignUp' }"><i class="bi bi-box-arrow-in-right"></i>
        </router-link>
      </a>
      <a v-if="!isLogin">
        <router-link :to="{ name: 'LogIn' }">LogIn</router-link>
      </a>
      <a v-if="isLogin">
        <router-link :to="{ name: 'Home' }" @click="logout"><i class="bi bi-person-slash"></i>
        </router-link>
      </a>
      <a v-if="isLogin">
        <router-link :to="{ name: 'RoadMap' }">
          <i class="bi bi-signpost-split-fill"></i>
        </router-link>
      </a>
      <a v-if="isLogin">
        <router-link :to="{ name: 'UserHome' }">
          <i class="bi bi-person-circle"></i>
        </router-link>
      </a>
    </div>
    <div class="menu" v-if="isAdmin">
      <a v-if="isLogin">
        <router-link :to="{ name: 'Home' }" @click="logout">로그아웃</router-link>
      </a>
      <a v-if="isLogin">
        <router-link :to="{ name: 'AdminHome' }">로드맵 작성 관리</router-link>
      </a>
      <a v-if="isLogin">
        <router-link :to="{ name: 'AdminUserView' }">전체 유저 관리</router-link>
      </a>
    </div>
  </nav>
<!-- </div> --></template>

<script>
import demoBtn from './demoBtn.vue';
export default {
  name: "Header",
  components: {
    demoBtn
  },
  data() {
    return {
      DemoOnOff: true,
      DemoMode: "DemoModeOff"
    }
  },
  computed: {

    isAdmin() {
      return this.$store.state.isAdmin;
    },
    isLogin() {
      return this.$store.state.isLogin;
    },
  },
  methods: {
    logout() {
      this.$store.commit('logout');
      window.localStorage.clear();
    },
    demoOnOff() {
      this.DemoOnOff = !this.DemoOnOff;
      if (this.DemoOnOff == true) {
        this.DemoMode = "DemoMode Off"
      } else {
        this.DemoMode = "DemoMode On"
      }
    }
  },
};
</script>

<style>
.menu {}

.mainLogo {
  color: black;
  text-align: left;
  font-size: 30px;
  text-decoration: none;
}

.menu a {
  color: black;
  padding: 20px;
  text-decoration: none;
}
</style>
