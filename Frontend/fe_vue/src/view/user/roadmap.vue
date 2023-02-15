<template>
  <h1 class="d-flex justify-content-center mt-4">
    나만의 로드맵
  </h1>
  <div class="roadmapBody">
    <div class="d-flex justify-content-center mt-4" style=" height: 80vh;overflow-y: scroll;">
      <div class="card-deck" style="width: 80vh">
        <div v-for="subject in subjects" :key="subject">
          <div class="card text-center mb-4">
            <div class="card-body">
              <button class="btn btn-dark card-title" @click="[
                $router.push({ name: 'CourseView', }), setCurrentSubjectId(subject.subjectId)
              ]" style="margin-right:auto;">
                {{ subject.name }}
              </button>
              <button class="btn ml-3" :class="getButtonClass(subject.mcqState)" type="button"
                @click="switchRouterByState(subject.mcqState, subject.mcqExamId, 'MCQ')">
                <span>객관식</span>
                <span class="bi bi-file-text"></span>
              </button>
              <button class="btn ml-3" :class="getButtonClass(subject.frqState)" type="button"
                @click="switchRouterByState(subject.frqState, subject.frqExamId, 'FRQ')">
                <span>주관식</span>
                <span class="bi bi-file-text"></span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- <div class="container">
    <div class="row align-items-center">
      <div class="row" v-for="subject in subjects" :key="subject">
        <div class="col col-md-7 offset-md-3
                            text-center mt-5">
          <button class="btn btn-dark" @click="[
          $router.push({
            name: 'CourseView',
          })
          , setCurrentSubjectId(subject.subjectId)]">
            {{ subject.name }}
          </button>
          <button class="btn ml-3" :class="getButtonClass(subject.mcqState)" type="button"
            @click="switchRouterByState(subject.mcqState, subject.mcqExamId, 'MCQ')">
            <span>객관식</span>
            <span class="bi bi-file-text"></span>
          </button>
          <button class="btn ml-3" :class="getButtonClass(subject.frqState)" type="button"
            @click="switchRouterByState(subject.frqState, subject.frqExamId, 'FRQ')">
            <span>주관식</span>
            <span class="bi bi-file-text"></span>
          </button>
        </div>
      </div>
    </div>
  </div> -->
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
          console.log("purchased button  exam id log : " + examId)
          this.$router.push({ name: 'McqExamView', params: { mcqExamId: examId } });
        } else {
          console.log("purchased button  exam id log : " + examId)
          this.$router.push({ name: 'FrqExamView', params: { frqExamId: examId } });
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
.roadmapBody {
  background-repeat: repeat-y;
  background-size: 40vh;
  background-position: center;
  background-image: url("@/assets/road_svg.svg");


}
</style>
