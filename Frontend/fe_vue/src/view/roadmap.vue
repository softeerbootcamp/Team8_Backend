<template>
    <div class="d-grid gap-2 col-6 mx-auto">
        <div v-for="subject in getRoadmapDetailFromStore()" :key="subject">
            <span v-for="course in subject" :key="course">
                <button class="btn btn-primary ms-3"
                    @click="[getCourseData(subject), $router.push({ name: 'CourseView', params: { subjectDetail: subjectDetail } })]">
                    {{ course[0] }}
                </button>
                <div v-if="(curSubjectId == course[1]) && isCardOn">
                    <router-view></router-view>
                </div>
            </span>

        </div>

    </div>


</template>

<script>
import axios from "axios";

export default {
    name: 'RoadMap',

    data() {
        console.log("rdetail" + this.props)
        return {
            isCardOn: false,
            success: false,
            subDataSuccess: false,
            roadmapId: "1",

            curSubjectId: "",

            subjectId: "subidtemp",
            subjectDetail: [],

        }

    },
    methods: {
        getRoadmapDetailFromStore() {
            return this.$store.state.roadmapDetail
        },
        setSubIdFromData(subid) {
            this.curSubjectId = subid;
        },
        getCourseData(subject) {
            this.isCardOn = true;
            console.log("subject : " + subject);
            var subid = subject.toString().split(',')[1];
            this.setSubIdFromData(subid);
            var vm = this;
            //api/roadmap/{:roadmapId}/{:subjectId}
            axios.get('https://backend.devroad.site/' + 'api/roadmap/' + this.roadmapId + '/' + subid)
                .then(response => {
                    console.log(response);
                    vm.subDataSuccess = response.data.success;
                    vm.subjectDetail = response.data.courses;
                })
                .catch(function (error) {
                    console.log(error)
                })
        },

    }


}
</script>

<style>

</style>
