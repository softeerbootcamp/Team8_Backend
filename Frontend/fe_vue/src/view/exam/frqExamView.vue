<template>
    <div class="d-flex justify-content-center align-items-center" style="height: 80vh;">
        <div class="card text-center" style="width: 500px;">
            <div class="card-header bg-primary text-white">
                <!-- {{ subjectName }} 시험지 --> 시험지
            </div>
            <div class="card-body">
                <p class="card-title">{{ examTitle }}</p>
                <hr>
                <p class="card-text font-weight-bold">요구사항</p>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">{{ examExplain }}</li>
                </ul>
            </div>
            <div class="card-footer">
                <a v-bind:href="examUrl">템플릿 코드를 다운로드하는 곳</a>
                <router-link :to="{
                    name: 'FrqExamSubmitView',
                    params: { examId: getExamId() }
                }">
                    <button class="btn btn-primary mt-2">제출하러가기</button>
                </router-link>
                <!-- <router-link :to="{ name: 'home' }" class="btn btn-secondary">Back</router-link> -->
            </div>
        </div>
    </div>
</template>
<script>
import { getExamDetailData } from '@/api'
export default {
    name: 'ExamView',
    data() {
        return {
            //subjectName: "",
            examUrl: "",
            examTitle: "",
            examExplain: "",
            examId: ""
        }
    },
    mounted() {
        this.getExamDetail();
        this.examId = this.$route.params.examId;

    },
    methods: {
        getExamId() {
            return this.$route.params.examId;
        },

        async getExamDetail() {
            const params = this.$route.params.examId;
            const config = {
                headers: {
                    jwt: this.$store.state.jwt
                }
            };
            await getExamDetailData(config, params)
                .then((response) => {
                    if (response.data.success) {
                        this.examUrl = response.data.examDetail.url;
                        this.examTitle = response.data.examDetail.name;
                        this.examExplain = response.data.examDetail.description;
                    } else {
                        console.log("데이터를 불러오는데 실패하였습니다!")
                    }

                })
                .catch(function (error) {
                    console.log(error);
                });
        },


    },
}
</script>
<style>

</style>