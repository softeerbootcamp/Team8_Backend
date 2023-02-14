<template>
  <div class="progress">
    <div class="progress-bar" role="progressbar" :style="`width: ${roadmapPercentage}%`"
      :aria-valuenow="roadmapPercentage" aria-valuemin="0" aria-valuemax="100">
      {{ roadmapPercentage }}%
    </div>
  </div>
  <router-link :to="{ name: 'RoadMap' }" v-if="!isRoadmapStarted" style="text-decoration: none;">
    <button class="btn btn-dark d-grid gap-2 col-2 mx-auto mt-4" style=" color:white;" v-if="!roadMapShowClicked">
      로드맵 시작하기!?
    </button>
  </router-link>
  <router-link :to="{ name: 'ChapterFrame',params:{chapterId :curChapterPK } }" v-if="isRoadmapStarted"
    style="text-decoration: none;">
    <button class="btn btn-dark d-grid gap-2 col-2 mx-auto mt-4">로드맵 이어하기!</button>
  </router-link>
</template>
<script>
import { userData } from '@/api'

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
      curChapterPK: 0,
      subscribe: false,
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
          this.userId = response.data.userId;
          this.userName = response.data.userName;
          this.roadmapId = response.data.roadmapId;
          if (this.roadmapId != "-1") {
            this.$store.commit('setIsRoadmap', response.data.roadmapId);
          }
          this.totalSubjectIdx = response.data.totalSubjectIdx;
          this.curSubjectIdx = response.data.curSubjectIdx;
          this.chapterPercent = response.data.chapterPercent;
          this.curChapterPK = response.data.curChapterPK;
          this.subscribe = response.data.subscribe;
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
        (this.chapterPercent)
      ) * 100;
    },

  },
};
</script>

<style>

</style>
