<template>
  <nav class="navbar sticky-top">
    <router-link to="/" class="mainLogo">DevRoad</router-link>
    <div class="menu" v-if="!isAdmin">
      <a v-if="!isLogin">
        <router-link to="/signup">회원가입</router-link>
      </a>
      <a v-if="!isLogin">
        <router-link to="/login">로그인</router-link>
      </a>
      <a v-if="isLogin">
        <router-link to="/" @click="logout">로그아웃</router-link>
      </a>
      <a v-if="isLogin">
        <router-link to="/roadmap">로드맵으로!</router-link>
      </a>
      <a v-if="isLogin">
        <router-link to="/userhome">{{ this.$store.state.username }}</router-link>
      </a>
    </div>
    <div class="menu" v-if="isAdmin">
      <a v-if="isLogin">
        <router-link to="/" @click="logout">로그아웃</router-link>
      </a>
      <a v-if="isLogin">
        <router-link to="/adminhome">로드맵 작성 관리</router-link>
      </a>
      <a v-if="isLogin">
        <router-link to="/adminuserview">전체 유저 관리</router-link>
      </a>
    </div>
  </nav>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
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
  },
};
</script>

<style>
.navbar {
  background-color: white;
}

.menu {
  background: black;
  padding: 10px;
  border-radius: 5px;

}

.mainLogo {
  color: black;
  text-align: left;
  font-size: 30px;
  text-decoration: none;
}

.menu a {
  color: white;
  padding: 20px;
  text-decoration: none;
}
</style>
