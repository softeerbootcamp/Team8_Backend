<template>
  <div class="row">
    <div class="offset-3 col-6">
      <form @submit.prevent="loginForm" class="col-12">
        <div class="col-12 mb-4">
          <label for="InputEmail" class="form-label">Email address</label>
          <input type="email" class="form-control" id="InputEmail" v-model="formData.email"
            aria-describedby="emailHelp" />
        </div>
        <div class="col-12 mb-4">
          <label for="InputPassword1" class="form-label">Password</label>
          <input type="password" class="form-control" v-model="formData.password" id="InputPassword1" />
        </div>
        <div class="col-12">
          <p v-if="isLoginFailed" class="text-center text-danger alert">{{ failMessage }}</p>
        </div>
        <div class="offset-3 col-6">
          <button type="submit" class="btn btn-dark d-grid gap-2 mx-auto" variant="primary">Submit</button>
        </div>

      </form>
    </div>
  </div>
</template>

<script>
// import axios from "axios";
import { signinUser } from "@/api";
import jwt_decode from "jwt-decode";

export default {
  name: "Login",
  data() {
    return {
      formData: {
        email: "",
        password: "",
      },
      isLoginFailed: false,
      failMessage: "",
    };
  },

  methods: {
    async loginForm() {
      const userData = {
        email: this.formData.email,
        password: this.formData.password
      };
      await signinUser(userData)
        .catch((error) => {
          if (error.response.status != 500) {
            this.isLoginFailed = true;
            this.failMessage = "아이디 또는 비밀번호가 일치하지 않습니다."
          }
          else {
            this.isLoginFailed = true;
            this.failMessage = "서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요."
          }

          console.log("login failed : " + error);
        })
        .then((response) => {
          if (response.data.success) {
            this.$store.commit("setLoginStatus", true);
            console.log("isLogin store status when login success! : " + this.$store.state.isLogin)
            this.$store.commit("setJwtToken", response.data.jwt);
            this.setStoreUserNameByJwt(response.data.jwt);
            console.log("username setted : " + this.$store.state.username)

            if (response.data.roadmapId === "-1") {
              console.log("userpending view roadmap id : " + response.data.roadmapId);
              this.$router.push({ name: "UserPendingView" });
            }
            if (response.data.admin) {
              this.$store.commit("setIsAdmin", true);
              this.$router.push({ name: "AdminHome" });

            } else {
              this.$router.push({ name: "UserHome" });

            }
          } else {
            this.isLoginFailed = true;
            console.log("login failed : " + response.data.success);
          }
        });

    },
    setStoreUserNameByJwt(jwt) {
      var username = this.jwtDecoder(jwt).username;
      this.$store.commit('setUsername', username)

    },
    jwtDecoder(jwt) {
      var decoded = jwt_decode(jwt);
      return decoded;
    }
  },
};
</script>

<style>
.alert {
  font-size: 0.7rem;
}
</style>
