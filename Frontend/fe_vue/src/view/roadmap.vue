<template>
    <h1>roadMap prototype</h1>
    <h2> is Success </h2>
    {{ isSuccess }}

    <h2>roadmapDetail</h2>
    <div class="d-grid gap-2 col-6 mx-auto">
        <div v-for="subject in roadmapDetail" :key="subject">
            <router-link :to="{ name: 'CourseView', params: { isCardOn: isCardOn} }">
                <span v-for="course in subject" :key="course">
                    <button class="btn btn-primary ms-3" @click="getCourseData(subject)">
                        {{ course[0] }}
                    </button>
                    <router-view v-if="curSubjectId==course[1]"></router-view>

                </span>
            </router-link>

        </div>

    </div>
    <button @click="getSubData" v-if="!roadMapShowClicked">
        temp btn for getting data
    </button>

</template>

<script>
import axios from "axios";

export default {

    data() {
        return {
            roadMapShowClicked: false,
            isCardOn: false,
            success: false,
            subDataSuccess:false,
            roadmapId: "temp",
            roadmapDetail: [],

            curSubjectId : "",

            subjectId: "subidtemp",
            subjectDetail: []

        }

    },
    methods: {
        setSubIdFromData(subid) {
            this.curSubjectId = subid;
        },
        /** 
         * roadmapDetail : 
         * "subjects" : 
    {
        //[name, id]
        "1" : [["기초프로그래밍", 1001]] ,
        "2" : [["파이썬 기초", 2001], ["자바의 기초",2002]],
        "3" : [["자료구조와 알고리즘", 3001], ["운영체제",3002]],
        "4" : [["김영한의 스프링", 4001 ]]
    }
         * 
         * 
         * subdetail : 
         * {
   "success" : "true",
   "courses" : 
   [ 
                {
            "subjectId":123, 
            "courseId":123, 
            "courseName":"name", 
            "thumbnailUrl":"url", 
            "courseUrl":"url", 
            "explain": "hello"
        } , { ... } 
    ]
   
}
         */
        getCourseData(subject) {
            this.isCardOn = true;
            console.log("subject : " +subject);
            var subid = subject.toString().split(',')[1];
            this.setSubIdFromData(subid);
            var vm = this;
            //api/roadmap/{:roadmapId}/{:subjectId}
            axios.get('http://127.0.0.1:5000/' + 'api/roadmap/' + this.roadmapId +'/' +subid)
                .then(function (response) {
                    console.log(response);
                    vm.subDataSuccess = response.data.success;
                    vm.subjectDetail = response.data.courses;
                })
                .catch(function (error) {
                    console.log(error)
                })
        },
        getSubData: function () {
            this.roadMapShowClicked = true;
            var vm = this;
            axios.get('http://127.0.0.1:5001/' + 'api/roadmap/' + this.roadmapId)
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
