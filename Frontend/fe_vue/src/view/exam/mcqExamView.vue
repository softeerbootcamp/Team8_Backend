<template>
    <div class="d-flex justify-content-center align-items-center" style="height: 80vh;">
        <div class="card text-center" style="width: 500px;">
            <div class="card-header bg-primary text-white">
                {{ subjectName }} 시험지
            </div>
            <div class="card-body">
                <p class="card-title">{{ title }}</p>
                <hr>
                <p class="card-text font-weight-bold">요구사항</p>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">{{ examExplain }}</li>
                </ul>
            </div>
            <form @submit="onSubmit">
                <div v-for="(question, index) in questions" :key="index">
                    <p class="card-text font-weight-bold">{{ question.title }}</p>
                    <p class="card-text">{{ question.content }}</p>
                    <div>
                        <input type="checkbox" v-model="myAns[index]" :value="choice" v-for="choice in question.choices"
                            :key="choice" /> {{ choice }}
                    </div>
                </div>
            </form>
            <div class="card-footer">
                <router-link :to="{ name: 'Roadmap' }">
                    <button type="submit" class="btn btn-primary mt-2">제출하기</button>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script>
import { getExamDetailData, sendExamResult } from '@/api';
export default {
    name: 'FrqExamView',
    data() {
        return {
            subjectName: "",
            title: "",
            description: "",
            questions: [],
            ans: [],
            myAns: [],
            isPassed: false,
            examId: "",
        }
    },
    mounted() {
        this.getExamDetail();
        this.examId = this.$route.params.examId;
    },
    methods: {
        async onSubmit() {
            var correct = 0;
            for (var i = 0; i < this.ans.length; i++) {
                if (this.ans[i] === this.myAns[i]) {
                    correct++;
                }
            }
            var result = (correct / this.ans.length) * 100;
            console.log("시험 결과  : " + result)
            if (result >= this.$store.state.passingScore) {
                this.isPassed = true;
            }
            const params = {
                examId: this.examId,
                result: this.isPassed
            };
            const config = {
                headers: {
                    jwt: this.$store.state.jwt
                }
            };
            await sendExamResult(config, params)
                .then((response) => {
                    if (response.data.success) {
                        console.log("테스트 제출 성공")
                        // 현재는 테스트 결과에 상관 없이 roadmap 으로 반환한다. 라우터 링크.
                        // this.$router.push('/roadmap');
                    } else {
                        console.log("테스트 실패")
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });


        },
        async getExamDetail() {
            const params = this.examId;
            const config = {
                headers: {
                    jwt: this.$store.state.jwt
                }
            };
            await getExamDetailData(config, params)
                .then((response) => {
                    if (response.data.success) {
                        this.subjectName = response.data.examDetail.subjectName;
                        this.title = response.data.examDetail.name;
                        this.description = response.data.examDetail.description;
                        this.questions = response.data.examDetail.questions;
                        this.ans = response.data.examDetail.ans;
                    } else {
                        console.log("데이터를 불러오는데 실패하였습니다!")
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
    }

}
</script>

<style>

</style>