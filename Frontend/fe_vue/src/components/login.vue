<template>
  <div>
    <form @submit.prevent="loginForm">
      <div class="mb-4 ps-5 pe-5">
        <label for="InputEmail" class="form-label">Email address</label>
        <input type="email" class="form-control" id="InputEmail" v-model="formData.email" aria-describedby="emailHelp">
      </div>
      <div class="mb-4 ps-5 pe-5">
        <label for="InputPassword1" class="form-label">Password</label>
        <input type="password" class="form-control" v-model="formData.password" id="InputPassword1">
      </div>

      <button type="submit" variant="primary" >Submit</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Login",
  data() {
    return {
      formData: {
        email: "",
        password: "",
      },
      loginSuccess: false
    };
  },

  methods: {
  

    async loginForm() {
      try {
        const response = await axios.post(
          "http://127.0.0.1:5002/"+"api/user/signin/",
          {
            "email": this.formData.email,
            "password": this.formData.password
          }
          ,
          {
            headers: {
              "Content-Type": "application/json"
            }
          }
        ).then(function (response) {
          if (response.data.success) {
            this.$store.commit('setLoginStatus', true)
            this.$router.push('/')
          }
        });
        if (response.status != 200) {
          console.log(response.data);
        }
      } catch (error) {
        //console.error(error);
      }
    }
  }
};
</script>
<style>

</style>