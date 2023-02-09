<template>
  <nav class="navbar sticky-top navbar-light bg-light">
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
    </div>
    <div class="menu" v-if="isAdmin">
      <a v-if="isLogin">
        <router-link to="/" @click="[logout(), adminLogout()]">로그아웃</router-link>
      </a>
    </div>
  </nav>
</template>

<script>
export default {
  name: "Header",
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
      this.$store.dispatch("logout");
    },
    adminLogout() {
      this.$store.dispatch("adminLogout");
    },
  },
};
</script>

<style>
.menu {
  background: darkslateblue;
  padding: 10px;
  border-radius: 5px;
  margin-right: 10px;
}

.mainLogo {
  color: darkslateblue;
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
