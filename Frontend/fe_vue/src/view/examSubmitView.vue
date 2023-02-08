<template>
    <div class="d-flex justify-content-center align-items-center" style="height: 80vh;">

        <div class="card text-center" style="width: 500px;">
            <div class="card-header bg-primary text-white">
                {{ subjectName }}
            </div>
            <div class="card-body">
                <p class="card-title">
                    제출란
                </p>
                <hr>
                <form>
                    <div class="form-group">
                        <label for="inputAnswer">정답</label>
                        <textarea class="form-control" id="inputAnswer" v-model="submitForm.explain" rows="10"
                            style="resize:none; font-size: 1.2rem;"></textarea>
                    </div>
                </form>
            </div>
            <div class="card-footer d-flex justify-content-between">
                <input type="text" class="form-control" id="inputAnswer" v-model="submitForm.url"
                    placeholder="Enter your gitrepo here" style="width: 70%">
                <router-link to="/roadmap">
                    <button type="submit" class="btn btn-primary" @click="postAssignMent">제출하기</button>
                </router-link>
            </div>
        </div>
    </div>
</template>
<script>
import { postAssignMentData } from '@/api'
export default {

    name: "ExamSubmitView",
    data() {
        return {
            subjectName: "",
            examId: "",
            submitForm: {
                url: "",
                explain: "// 프로젝트에 대한 간단한 설명\n"
                    + "// 각각의 파일이 무슨일을 하는지 적어주세요.\n"
                    + "// 해당 시험 진행하면서 어려웠던 점이나 고민했던 부분을 적어주세요.\n"
                    + "// 해당 과정을 들으면서 본인이 무엇을 배웠고 얼마나 성장했는지 적어주세요\n",
                examId: ""
            }
        }
    },
    mounted() {
        this.subjectName = this.$route.params.subjectName;
        this.examId = this.$route.params.examId;
    },
    methods: {
        async postAssignMent() {
            const config = {
                headers: {
                    jwt: this.$store.state.jwt
                }
            };
            const params = {
                url: this.submitForm.url,
                explain: this.submitForm.explain,
                examId: this.examId
            };
            await postAssignMentData(config, params)
                .then((response) => {
                    if (response.data.success) {
                        console.log("테스트 제출 완료!")
                    }

                })
                .catch(function (error) {
                    console.log(error);
                });
        }
    },
}
</script>

<style>

</style>