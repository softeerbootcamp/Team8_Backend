<template>
    <div class="row justify-content-center text-center" justify-content-center>
        <iframe class="col-12" src="https://docs.google.com/forms/d/e/1FAIpQLSfrxSZ47GKSVhye3hIXoFVHGmQSvA8yzUEhY01-N91e4Gwtwg/viewform?embedded=true" width="640" height="750" frameborder="0" marginheight="0" marginwidth="0">로드 중…</iframe>

            <p>설문조사를 완료하시면 눌러주세요!</p>
            <button class="col-2 btn btn-dark" @click="this.$router.push({ name: 'UserHome' })">완료</button>      

    </div>
</template>

<script>
export default {
    name: "Survey",
    data() {
        return {
            showLogin: false,
        };
    },
    methods: {
        showLoginForm() {
            this.showLogin = true;
        },
        closeLoginForm() {
            this.showLogin = false;
        },
    },
};
</script>

<style>
.prime{
    color : #B01E68;
}
</style>