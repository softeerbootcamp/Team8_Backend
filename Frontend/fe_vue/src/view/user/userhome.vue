<template>
  <div class="progress">
    <div class="progress-bar" role="progressbar" :style="`width: ${roadmapPercentage}%`"
      :aria-valuenow="roadmapPercentage" aria-valuemin="0" aria-valuemax="100">
      {{ roadmapPercentage }}%
    </div>
  </div>

  <router-link :to="{ name: 'RoadMap' }" v-if="!isRoadmapStarted" style="text-decoration: none;">
    <!-- <router-link :to="{ name: 'RoadMap' }" v-if="isRoadmapStarted"> -->
    <button class="btn btn-dark d-grid gap-2 col-2 mx-auto mt-4" style=" color:white;" @click="getSubData"
      v-if="!roadMapShowClicked">
      로드맵 시작하기!?
    </button>
  </router-link>
  <router-link :to="{ name: 'ChapterView' }" v-if="isRoadmapStarted">
    <button>로드맵 이어하기!</button>
  </router-link>
</template>
<script>
import { userData, getRoadmap } from '@/api'

export default {
  name: "UserHome",
  data() {
    return {
      roadmapPercentage: 0,
      roadMapShowClicked: false,
      subjects: [],
      isSuccess: false,
      userId: null,
      userName: null,
      roadmapId: "1",
      totalSubjectIdx: 0,
      curSubjectIdx: 0,
      chapterPercent: 0,
      nextChapterPK: 0,
      jwt: null,
    };
  },
  mounted() {
    this.setProgressbar();
    this.getUserData();

  },
  computed: {
    isRoadmapStarted() {
      if (this.chapterPercent != 0) {
        return true;
      }
      return false;
    },
  },
  methods: {
    async getUserData() {
      const config = {
        headers: {
          jwt: this.$store.state.jwt
        }
      };
      await userData(config)
        .then((response) => {
          console.log("user data jwt : " + config);
          this.$store.commit('setAccountId', response.data.userId);
          //       "success" : "true",
          // "userId" : "id",
          // "userName" : "name",
          // "roadmapId" : "roadmapId",
          // "totalSubjectIdx" : "4",
          // "curSubjectIdx" : "1",
          // "chapterPercent" : 0.25,
          // "nextChapterPK" : 1001
          this.userId = response.data.userId;
          this.userName = response.data.userName;
          this.roadmapId = response.data.roadmapId;
          this.totalSubjectIdx = response.data.totalSubjectIdx;
          this.curSubjectIdx = response.data.curSubjectIdx;
          this.chapterPercent = response.data.chapterPercent;
          this.nextChapterPK = response.data.nextChapterPK;
          this.setProgressbar();
        })
        .catch(function (error) {
          console.log(error);
        });


    },
    setProgressbar() {
      if (this.chapterPercent == 0) {
        this.roadmapPercentage = 0;
        return;
      }
      this.roadmapPercentage = parseInt(
        (this.chapterPercent) * 100
      );
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
          this.$store.commit("setSubjectsStatus", response.data.subjects);
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
