<template>
    <div>
      <h1>Sign Up</h1>
      <!-- Sign Up form here -->
      <form @submit.prevent="submitForm">
        <input type="text" v-model="formData.email" placeholder="Email" />
        <input type="password" v-model="formData.password" placeholder="Password" />
        <input type="text" v-model="formData.name" placeholder="Name" />
        <input type="text" v-model="formData.phoneNumber" placeholder="Phone Number" />
        <button type="submit">Sign Up</button>
      </form>
    </div>
  </template>

<script>
import axios from "axios";

export default {
  name: "SignUp",
  data() {
    return {
      formData: {
        email: "",
        password: "",
        name: "",
        phoneNumber: ""
      }
      
    };
  },

  methods: {
    async submitForm() {
      try {
        const response = await axios.post("http://127.0.0.1:5000/", this.formData,{headers: {
                    "Content-Type": "multipart/form-data" }}
);
        console.log(response.data);
      } catch (error) {
        console.error(error);
      }
    }
  }
};
</script>
<style>
</style>