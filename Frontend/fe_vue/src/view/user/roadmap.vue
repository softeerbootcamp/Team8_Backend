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
              <button class="btn" :class="getButtonClass(subject.mcqState)" type="button"
                @click="switchRouterByState(subject.mcqState, subject.mcqExamId, 'MCQ')" style="margin:10px">
                <span>객관식</span>
                <span class="bi bi-file-text"></span>
              </button>
              <button class="btn" :class="getButtonClass(subject.frqState)" type="button"
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
  <reviewSelectModal @card-selected="onCardSelected" @close="closeModal" v-if="showReviewSelectModal">
  </reviewSelectModal>
  <reviewAiModal @card-selected="onCardSelected" @close="showReviewAiModal = false" v-if="showReviewAiModal">
  </reviewAiModal>
  <reviewPeerModal @card-selected="onCardSelected" @close="showReviewPeerModal = false" v-if="showReviewPeerModal">
  </reviewPeerModal>
</template>

<script>
import { getRoadmap } from '@/api'
import reviewSelectModal from '@/components/reviewSelectModal.vue'
import reviewAiModal from '@/components/reviewAiModal.vue'
import reviewPeerModal from '@/components/reviewPeerModal.vue';
export default {
  name: "RoadMap",
  components: {
    reviewSelectModal,
    reviewAiModal,
    reviewPeerModal
  },
  data() {
    return {
      isCardOn: false,
      success: false,
      subDataSuccess: false,

      isSuccess: false,
      subjects: [],

      showReviewSelectModal: false,
      showReviewAiModal: false,
      showReviewPeerModal: false,
      frqExamIdForModal: "",
    };
  },
  mounted() {
    this.getSubData();
  },
  methods:
  {
    openReviewModal() {
      this.showReviewSelectModal = true
    },
    closeModal() {
      this.showReviewSelectModal = false
    },
    onCardSelected(cardClass) {
      this.selectedReviewType = cardClass;
      console.log("cardClass : " + cardClass);
      if (cardClass === 'gobackToSelectFromPeer') {
        this.showReviewPeerModal = false;
        this.showReviewSelectModal = true;

      } else if (cardClass === 'gobackToSelectFromAi') {
        this.showReviewAiModal = false;
        this.showReviewSelectModal = true;
      }
      else {
        this.showReviewSelectModal = false
        if (cardClass === 'ai') {
          this.showReviewAiModal = true
        }
        if (cardClass === 'peer') {
          this.showReviewPeerModal = true
        }
      }


    },

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
      if (state === 'NONE') {
        this.$router.push({ name: "ExamPurchaseView", params: { examId: examId } });
      }
      if (state === 'PASSED') {
        if (isMcqOrFrq == 'FRQ') {
          this.$store.commit('setCurSubjectExamId', examId);
          this.openReviewModal();
        }
      }
      if (state === 'PURCHASED') {
        if (isMcqOrFrq === 'MCQ') {
          console.log("purchased button  exam id log : " + examId)
          this.$router.push({ name: 'McqExamView', params: { mcqExamId: examId } });
        } else {
          console.log("purchased button  exam id log : " + examId)
          this.$router.push({ name: 'FrqExamView', params: { frqExamId: examId } });
        }
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
