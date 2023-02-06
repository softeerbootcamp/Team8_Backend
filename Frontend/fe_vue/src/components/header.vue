<template>
  <router-link to="/" class="mainLogo">DevRoad</router-link>
  <div class="menu">
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
</template>

<script>
export default {
  name: "Header",
  computed: {
    isLogin() {
      return this.$store.state.isLogin;
    },
  },
  methods: {
    logout() {
      this.$store.dispatch("logout");
    },
  },
};
</script>

<style></style>
