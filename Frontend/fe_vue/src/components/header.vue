<template>
    <router-link to="/" class="mainLogo">DevRoad</router-link>
    <div class="menu">
        <a v-if="!getIsLogin">
            <router-link to="/signup">회원가입</router-link>
        </a>
        <a v-if="!getIsLogin">
            <router-link to="/login">로그인</router-link>
        </a>
        <a v-if="getIsLogin">
            <router-link to="/">로그아웃</router-link>
        </a>

    </div>
</template>
<script>
export default {
    name: 'Header',
    computed:{
        getIsLogin(){
            return this.$store.state.isLogin
        }
    },
    data() {
        return {
        }
    },
   
}
</script> 

<style>

</style>