<template>
    <button class="btn" @click="loginDemoStudentId_noRoadmap">
        학생_로드맵x
    </button>
    <button class="btn" @click="loginDemoAdminId">
        관리자
    </button>
    <button class="btn" @click="loginDemoStudentId_NotSub">
        학생_비구독
    </button>
    <button class="btn" @click="loginDemoStudentId">
        학생_구독
    </button>
</template>
<script>
import { signinUser } from "@/api";
import jwt_decode from "jwt-decode";

export default {
    name: 'DemoBtn',
    data() {
        return {
            isLoginFailed: false,
            failMessage: "",
        }
    },
    methods: {
        logout() {
            this.$store.commit('logout');
            window.localStorage.clear();
        },
        loginDemoStudentId_NotSub() {
            this.logout();
            this.loginForm("notPurchase@naver.com", "1234");
            this.$router.push('/');
        },
        loginDemoStudentId_noRoadmap() {
            this.logout();
            this.loginForm("RoadmapNo@naver.com", "1234");
            this.$router.push('/');
        },
        loginDemoStudentId() {
            this.logout();
            this.loginForm("jm1234@naver.com", "jm1234");
            this.$router.push('/');
        },
        loginDemoAdminId() {
            this.logout();
            this.loginForm("admin@naver.com", "1234");
            this.$router.push('/');
        },
        async loginForm(email, pwd) {
            const userData = {
                email: email,
                password: pwd
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
                        this.$store.commit("setJwtToken", response.data.jwt);
                        this.setStoreUserNameByJwt(response.data.jwt);

                        if (response.data.roadmapId === "-1") {
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
        },

    }

}
</script>
<style></style>