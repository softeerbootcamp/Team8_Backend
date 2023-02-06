<template>
  <div class="d-grid gap-2 col-6 mx-auto">
    <div class="card" style="width: 18rem">
      <div class="card-body">
        <div class="card-title">코스 정보</div>
        <p class="card-text">{{ courseData }}!!!</p>
        <a href="#" class="btn btn-primary">Go somewhere</a>
      </div>
    </div>
  </div>
</template>
<script>
import { getSubjectDetail } from "@/api";

export default {
  props: ["subject"],
  data() {
    return {
      courseSubject: this.subject,
      subjectId: "",
      courseData: "",
      // subject detail = courses
      subjectDetail: null,
      subDataSuccess: false,
      // subid 는 이후 받아와야한다.
      subid: "1",
    };
  },
  mounted() {
    this.getCourseData(this.courseSubject);
  },
  methods: {
    // subjectID ex ) "1001"
    async getCourseData(subject) {
      console.log("subject!!! : " + subject);
      this.subid = subject[1];
      // 선택된 subject 정보를 다음 라우터에 넘겨주기 위해 curSubId 설정
      var vm = this;
      //GET /api/roadmap/{:subjectId}/ sub Detail

      await getSubjectDetail(vm.subid)
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
