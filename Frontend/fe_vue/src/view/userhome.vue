<template>
    <router-link to="/roadmap">
    <button @click="getSubData" v-if="!roadMapShowClicked" >
        로드맵 시작하기!
    </button>
</router-link>
</template>

<script>
import axios from "axios";

export default {
    name : 'UserHome',
    data() {
        return {
            roadMapShowClicked: false,
            roadmapId : "1",
            roadmapDetail:[],
            isSuccess : false
        }

    },
    computed:{
        // getIsLogin(){
        //     return this.$store.state.isLogin
        // }
    },
    methods:{
        getSubData: function () {
            var vm = this;
            axios.get('https://backend.devroad.site/' + 'api/roadmap/' + this.roadmapId)
                .then(response => {
                    console.log(response);
                    vm.isSuccess = response.data.isSuccess;
                    vm.roadmapDetail = response.data.roadmapDetail;

                })
                .catch(function (error) {
                    console.log(error)
                })
        }
    }
}
</script>

<style>

</style>