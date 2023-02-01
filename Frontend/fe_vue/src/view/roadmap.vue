<template>
    <h1>roadMap prototype</h1>
    <h2> is Success </h2>
    {{ isSuccess }}

    <h2>roadmapDetail</h2>
    <div class="d-grid gap-2 col-6 mx-auto" >
        <div v-for="subject in roadmapDetail" :key="subject"> 
            <button class="btn btn-primary ms-3" type="button" v-for="course in subject" :key="course"> 
                {{ course[0] }}
            </button>
        </div>

    </div>

    <button @click="getData" v-if="!roadMapShowClicked">
        temp btn for getting data
    </button>

</template>

<script>
import axios from "axios";

export default {

    data() {
        return {
            roadMapShowClicked : false,
            isSuccess: false,
            roadmapDetail: [],
            roadmapId : "temp"
        }

    },
    methods: {

        getData: function () {
            this.roadMapShowClicked = true;
            var vm = this;
            axios.get('http://127.0.0.1:5000/'+'api/roadmap/'+this.roadmapId)
                .then(function (response) {
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