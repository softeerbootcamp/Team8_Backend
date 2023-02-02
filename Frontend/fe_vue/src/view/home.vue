<template>

  <router-view />
</template>

<script>
export default {
    name: 'Home',
    data() {
      return {
       
      }
    },
    methods: {
  
    },
    computed: {
    isLogin() {
      return this.$store.state.isLogin
    }
  }
   
  }
</script> 
<style>
  
</style>
  