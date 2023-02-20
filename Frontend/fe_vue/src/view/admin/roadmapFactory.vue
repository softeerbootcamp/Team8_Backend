
<template>
    <div class="container mt-5">
        <h1 class="text-center">유저 로드맵 생성 페이지</h1>
        <form class="mt-5" @submit="onSubmit" @reset="onReset">
            <div v-for="i in roadmapSubjectCnt" :key="i">
                <div class="form-group">
                    <label for="subject">Subject:</label>
                    <select id="subject" class="form-control" v-model="selectedSubjects[i]" required>
                        <option value="" disabled>Select One</option>
                        <option v-for="subject in subjects" v-bind:value="subject" v-bind:key="subject.id"
                            :disabled="selectedSubjects.includes(subject)">{{
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
import { getAllSubjectData, postRoadmapToUserByEmail } from '@/api'

export default {
    name: "RoadMapFactory",
    data() {
        return {
            roadmapSubjectCnt: 2,
            selectedSubjects: [],
            subjects: []
        };
    },
    mounted() {
        this.selectedSubjects = [];
        this.getAllSubjectDetail();
    },
    methods: {
        addRoadmapSubjectCnt() {
            this.roadmapSubjectCnt = this.roadmapSubjectCnt + 1;
        },
        subtractRoadmapSubjectCnt() {
            this.roadmapSubjectCnt = this.roadmapSubjectCnt - 1;

        },
        async onSubmit(event) {
            event.preventDefault();
            const userEmail = this.$route.params.userEmail;
            const subjectSequence = this.selectedSubjects.filter(subject => subject !== null).map(subject => subject.id);
            const config = {
                headers: {
                    jwt: this.$store.state.jwt
                }
            };
            const data = {
                email: userEmail,
                subjectSequence: subjectSequence
            };

            await postRoadmapToUserByEmail(config, data)
                .then((response) => {
                    if (response.data.success) {
                        this.$router.push({ name: "AdminHome" });
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });


        },
        onReset(event) {
            event.preventDefault();
            this.selectedSubjects = [];
        },
        async getAllSubjectDetail() {
            const config = {
                headers: {
                    jwt: this.$store.state.jwt
                }
            };
            await getAllSubjectData(config)
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