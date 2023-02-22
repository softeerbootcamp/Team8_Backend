<template>
    <div class="row" justify-content-center>
        <iframe src="https://docs.google.com/forms/d/e/1FAIpQLSfrxSZ47GKSVhye3hIXoFVHGmQSvA8yzUEhY01-N91e4Gwtwg/viewform?embedded=true" width="640" height="615" frameborder="0" marginheight="0" marginwidth="0">로드 중…</iframe>
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
    
</style>