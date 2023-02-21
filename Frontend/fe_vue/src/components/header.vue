<template>
  <nav class="navbar sticky-top">
    <button class="btn" style="position:absolute;left:90%;" @click="demoOnOff">
      {{ DemoMode }}
      <demo-btn v-if="DemoOnOff"></demo-btn>
    </button>
    <div class="menu" v-if="!isAdmin">
      <router-link to="/" class="mainLogo" style="color : #B01E68">DevRoad</router-link>


      <a v-if="!isLogin">
        <router-link :to="{ name: 'SignUp' }" style="color : #B01E68;"><i class="bi bi-box-arrow-in-right"></i>
        </router-link>
      </a>
      <a v-if="!isLogin">
        <router-link :to="{ name: 'LogIn' }" style="color : #B01E68;">LogIn</router-link>
      </a>
      <a v-if="isLogin">
        <router-link :to="{ name: 'Home' }" @click="logout">
          <i class="bi bi-person-slash" style="color : #B01E68;font-size: 26px;"></i>
        </router-link>
      </a>
      <a v-if="isLogin">
        <router-link :to="{ name: 'RoadMap' }">
          <i class="bi bi-signpost-split-fill" style="color : #B01E68;font-size: 26px;"></i>
        </router-link>
      </a>
      <a v-if="isLogin">
        <router-link :to="{ name: 'UserHome' }">
          <i class="bi bi-person-circle" style="color : #B01E68;font-size: 26px;"></i>
        </router-link>
      </a>
    </div>
    <div class="menu" v-if="isAdmin">
      <router-link to="/" @click="$router.go(0)" class="mainLogo" style="color : #B01E68">DevRoad</router-link>

      <a v-if="isLogin">
        <router-link :to="{ name: 'Home' }" @click="logout">
          <i class="bi bi-person-slash" style="color : #B01E68;font-size: 26px;"></i>
        </router-link>
      </a>
      <a v-if="isLogin">
        <router-link :to="{ name: 'AdminHome' }">
          <i class="bi bi-signpost-split" style="color : #B01E68;font-size: 26px;"></i>
        </router-link>

      </a>
      <a v-if="isLogin">
        <router-link :to="{ name: 'AdminUserView' }">
          <i class="bi bi-people-fill" style="color : #B01E68;font-size: 26px;"></i>
        </router-link>
      </a>
    </div>

  </nav>
</template>

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
.menu {
  border-top: 40px;
  width: 70vw;
  margin: 20px auto;
}

.menu:after {
  content: "";
  display: block;
  border-bottom: 2px solid #B01E68;
}

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
