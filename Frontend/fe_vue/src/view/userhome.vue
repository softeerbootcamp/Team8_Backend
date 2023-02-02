<template>
    <router-link :to="{ name: 'RoadMap'}">
        <button @click="getSubData" v-if="!roadMapShowClicked">
            로드맵 시작하기!
        </button>
    </router-link>
</template>

<script>
import axios from "axios";
export default {
    name: 'UserHome',
    data() {
        return {
            roadMapShowClicked: false,
            roadmapId: "1",
            roadmapDetail: [],
            isSuccess: false
        }

    },
    methods: {
        getSubData: function () {
            var vm = this;
            axios.get('https://backend.devroad.site/' + 'api/roadmap/' + this.roadmapId,
                {
                    headers: {
                        "jwt": "jwt"
                    }
                })
                .then(response => {
                    console.log(response);
                    vm.isSuccess = response.data.success;
                    vm.roadmapDetail = response.data.subjects;
                    this.$store.commit("setRoadmapDetailStatus", response.data.subjects);

                })
                .catch(function (error) {
                    console.log(error)
                })
        },
    },

}
</script>

<style>

</style>