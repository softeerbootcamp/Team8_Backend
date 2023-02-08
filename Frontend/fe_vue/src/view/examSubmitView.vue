<template>
    <div>
        제출 페이지
    </div>
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
                        <input type="text" class="form-control" id="inputAnswer" placeholder="Enter your answer here">
                    </div>
                </form>
            </div>
            <div class="card-footer">
                <!-- <a v-bind:href="examUrl">템플릿 코드를 다운로드하는 곳</a> -->
                <router-link to="/roadmap">
                    <button class="btn btn-primary mt-2">제출하기</button>
                </router-link>
                <!-- <router-link :to="{ name: 'home' }" class="btn btn-secondary">Back</router-link> -->
            </div>
        </div>
    </div>

</template>

<script>
export default {
    name: "ExamSubmitView",
    data() {
        return {
            subjectName: ""
        }
    },
    mounted() {
        this.subjectName = this.$route.params.subjectName;
    },
}
</script>

<style>

</style>