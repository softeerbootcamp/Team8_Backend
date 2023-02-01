<template>
  <!-- <div>
      <h1>Sign Up</h1>
      <form @submit.prevent="submitForm">
        <li>
        <input type="text" v-model="formData.email" placeholder="Email" />
        <input type="password" v-model="formData.password" placeholder="Password" />
        <input type="text" v-model="formData.name" placeholder="Name" />
        <input type="text" v-model="formData.phoneNumber" placeholder="Phone Number" />
        <button type="submit">Sign Up</button>
      </li>
      </form>
    </div> -->
  <div>
    <form @submit.prevent="submitForm">
      <div class="mb-4 ps-5 pe-5">
        <label for="InputEmail" class="form-label">Email address</label>
        <input type="email" class="form-control" id="InputEmail" v-model="formData.email" aria-describedby="emailHelp">
      </div>
      <div class="mb-4 ps-5 pe-5">
        <label for="InputPassword1" class="form-label">Password</label>
        <input type="password" class="form-control" v-model="formData.password" id="InputPassword1">
      </div>
      <div class="mb-4 ps-5 pe-5">
        <label for="InputName" class="form-label">Name</label>
        <input type="text" class="form-control" v-model="formData.name" id="InputEmail1" aria-describedby="emailHelp">
      </div>
      <div class="mb-4 ps-5 pe-5">
        <label for="InputPhoneNumber" class="form-label">PhoneNumber</label>
        <input type="text" class="form-control" v-model="formData.phoneNumber" id="InputPhoneNumber">
      </div>

      <button type="submit" variant="primary">Submit</button>
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
        const response = await axios.post("http://127.0.0.1:5000/", this.formData, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        }
        );
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