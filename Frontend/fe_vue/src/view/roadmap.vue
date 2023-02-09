<template>
  <div class="d-grid gap-2 col-6 mx-auto">
    <div v-for="subject in getsubjects" :key="subject">
      <!-- subject안에는 여러 course들이 존재한다 -->
      <span v-for="course in subject" :key="course">
        <button class="btn btn-primary ms-3" @click="[
        $router.push({
          name: 'CourseView',
        })
        , setCurrentSubjectId(course)]">
          {{ course[0] }}
        </button>
        <router-link :to="{
          name: 'ExamView',
          params: { examId: course[3] }
        }" style="text-decoration: none; color:black;">
          <button :class="getButtonClass(course[2])" type="button">
            <span class="bi bi-file-text"></span>
          </button>
        </router-link>

      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "RoadMap",

  data() {
    return {
      isCardOn: false,
      success: false,
      subDataSuccess: false,
    };
  },

  computed: {
    // 여기서 course 는 클릭한 현재 sub 이다.

    getsubjects() {
      return this.$store.state.subjects;
    }
  },
  methods:
  {
    setCurrentSubjectId(course) {
      var courseStr = String(course);
      const subjectID = courseStr.split(',')[1];
      console.log("subjec id setting : " + subjectID);
      return this.$store.commit("setCurSubjectId", subjectID);
    },
    getButtonClass(state) {
      switch (state) {
        case 'NONE':
          return 'btn btn-light btn-sm';
        case 'PURCHASED':
          return 'btn btn-info btn-sm';
        case 'SUBMITTED':
          return 'btn btn-warning btn-sm';
        case 'PASSED':
          return 'btn btn-success btn-sm';
        case 'FAILED':
          return 'btn btn-danger btn-sm';
        default:
          return 'btn btn-light btn-sm';
      }
    },
  }
};
</script>

<style>

</style>
