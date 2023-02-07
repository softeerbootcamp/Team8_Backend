
<template>
    <div class="container mt-5">
        <h1 class="text-center">유저 로드맵 생성 페이지</h1>
        <form class="mt-5" @submit="onSubmit" @reset="onReset">
            <div v-for="i in roadmapSubjectCnt" :key="i">
                <div class="form-group">
                    <label for="subject">Subject:</label>
                    <select id="subject" class="form-control" v-model="selectedSubjects[i]" required>
                        <option value="" disabled>Select One</option>
                        <option v-for="subject in subjects" v-bind:value="subject" v-bind:key="subject.id">{{
                            subject.name
                        }}</option>
                    </select>
                </div>
            </div>
            <button class="btn btn-primary" type="submit">Submit</button>
            <button class="btn btn-secondary" type="reset">Reset</button>
        </form>
        <div class="d-flex justify-content-between mt-3">
            <button class="btn btn-primary" @click="addRoadmapSubjectCnt">
                Subject 추가하기
            </button>
            <button class="btn btn-danger" @click="subtractRoadmapSubjectCnt">
                Subject 빼기
            </button>
        </div>
    </div>
</template>
<script>
import { getAllSubjectData } from '@/api'

export default {
    name: "RoadMapFactory",
    data() {
        return {
            roadmapSubjectCnt: 2,
            selectedSubjects: [],
            subjects: []
        };
    },
    methods: {
        addRoadmapSubjectCnt() {
            this.roadmapSubjectCnt = this.roadmapSubjectCnt + 1;
        },
        subtractRoadmapSubjectCnt() {
            this.roadmapSubjectCnt = this.roadmapSubjectCnt - 1;

        },
        onSubmit(event) {
            event.preventDefault();
            const formData = {
                subjectSequence: this.selectedSubjects
            }
            alert(JSON.stringify(formData));
        },
        onReset(event) {
            event.preventDefault();
            this.selectedSubjects = [];
        },
        async getAllSubjectDetail() {
            await getAllSubjectData()
                .then((response) => {
                    this.subjects = response.data.subjects;
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    },
    created() {
        this.getAllSubjectDetail();
    }
};
</script>