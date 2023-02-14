<template>
  <div class="d-grid gap-2 col-6 mx-auto mt-4">
    <div v-for="subject in subjects" :key="subject">
      <button class="btn btn-primary ms-3 mt-4" @click="[
      $router.push({
        name: 'CourseView',
      })
      , setCurrentSubjectId(subject.subjectId)]">
        {{ subject.name }}
      </button>
      <button class="btn mt-4" :class="getButtonClass(subject.mcqState)" type="button"
        @click="switchRouterByState(subject.mcqState, subject.mcqExamId, 'MCQ')">
        <span class="bi bi-file-text"></span>
      </button>
      <button class="btn mt-4" :class="getButtonClass(subject.frqState)" type="button"
        @click="switchRouterByState(subject.frqState, subject.frqExamId, 'FRQ')">
        <span class="bi bi-file-text"></span>
      </button>
    </div>
  </div>
</template>

<script>
import { getRoadmap } from '@/api'
export default {
  name: "RoadMap",

  data() {
    return {
      isCardOn: false,
      success: false,
      subDataSuccess: false,

      isSuccess: false,
      subjects: [],
    };
  },
  mounted() {
    this.getSubData();
  },
  methods:
  {
    async getSubData() {
      const config = {
        headers: {
          jwt: this.$store.state.jwt,
        }
      }
      var vm = this;
      await getRoadmap(config)
        .then((response) => {
          console.log("Getroadmap!!data : " + response.data.success)
          if (!response.data.success) {
            this.$router.push('/');
          }
          vm.isSuccess = response.data.success;
          vm.subjects = response.data.subjects;
          this.$store.commit("setSubjectsStatus", response.data.subjects);
        })
        .catch(function (error) {
          console.log(error);
        });
    },

    setCurrentSubjectId(subId) {
      // var courseStr = String(course);
      // const subjectID = courseStr.split(',')[1];
      console.log("subjec id setting : " + subId);
      return this.$store.commit("setCurSubjectId", subId);
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
    switchRouterByState(state, examId, isMcqOrFrq) {
      // switch(state) {
      //   case 'NONE':
      //     return 'btn btn-light btn-sm';
      //   case 'PURCHASED':

      //   case 'SUBMITTED':
      //     return 'btn btn-warning btn-sm';
      //   case 'PASSED':
      //     return 'btn btn-success btn-sm';
      //   case 'FAILED':
      //     return 'btn btn-danger btn-sm';
      //   break:
      //     return 'btn btn-light btn-sm';
      // }

      if (state === 'PURCHASED') {
        if (isMcqOrFrq === 'MCQ') {
          this.$router.push({ name: "McqExamView", params: { examId: examId } });
        } else {
          this.$router.push({ name: "FrqExamView", params: { examId: examId } });
        }
      }
      if (state === 'NONE') {
        this.$router.push({ name: "ExamPurchaseView", params: { examId: examId } });
      }
    }
  }
};
</script>

<style>

</style>
