<template>
  <div class="row row-cols-1 row-cols-md-3 g-4">
    <div class="col" v-for="course in subjectDetail" :key="course.id">
      <div class="card">
        <img :src="course.thumbnailUrl" class="card-img-top" alt="...">
        <div class="card-body">
          <h5 class="card-title">{{ course.courseName }}</h5>
          <p class="card-text">Tutor: {{ course.tutorName }}</p>
          <p class="card-text">Explain: {{ course.explain }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getSubjectDetail } from "@/api";

export default {
  data() {
    return {
      curSubject: "",
      subjectId: "",
      // course 정보들
      courseData: "",
      courseName: "",
      tutorName: "",
      thumbnailUrl: "",
      explain: "",
      finish: "",
      // subject detail = courses
      subjectDetail: null,
      subDataSuccess: false,
      subid: "",
    };
  },
  mounted() {
    this.getCourseData();
  },
  methods: {
    // subjectID ex ) "1001"
    async getCourseData() {
      console.log("this.$store.state.curSubjectId : " + this.$store.state.curSubjectId);
      this.subid = this.$store.state.curSubjectId;
      console.log("subid!!!! : " + this.subid);

      // 선택된 subject 정보를 다음 라우터에 넘겨주기 위해 curSubId 설정
      var vm = this;
      //GET /api/roadmap/{:subjectId}/ sub Detail
      const config = {
        headers: {
          jwt: this.$store.state.jwt
        }
      };
      await getSubjectDetail(vm.subid, config)
        .then((response) => {
          console.log(response);
          vm.subDataSuccess = response.data.success;
          vm.subjectDetail = response.data.courses;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>
<style>

</style>
