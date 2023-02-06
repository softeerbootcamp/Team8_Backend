<template>
  <div class="progress">
    <div
      class="progress-bar"
      role="progressbar"
      :style="`width: ${roadmapPercentage}%`"
      :aria-valuenow="roadmapPercentage"
      aria-valuemin="0"
      aria-valuemax="100"
    >
      {{ roadmapPercentage }}%
    </div>
  </div>
  <router-link :to="{ name: 'RoadMap' }" v-if="!isRoadmapStarted">
    <button @click="getSubData" v-if="!roadMapShowClicked">
      로드맵 시작하기!?
    </button>
  </router-link>
  <router-link :to="{ name: 'ChapterView' }" v-if="isRoadmapStarted">
    <button>로드맵 이어하기!</button>
  </router-link>
</template>
<script>
import axios from "axios";

export default {
  name: "UserHome",
  data() {
    return {
      max: 100,
      roadmapPercentage: 0,
      roadMapShowClicked: false,
      roadmapDetail: [],
      isSuccess: false,
      userId: null,
      userName: null,
      roadmapId: "1",
      totalSubjectIdx: 0,
      curSubjectIdx: 0,
      totalChapterIdx: 0,
      curChapterIdx: 0,
    };
  },
  mounted() {
    this.getUserData();
  },
  methods: {
    isRoadmapStarted() {
      if (this.totalChapterIdx != 0) {
        return true;
      }
      return false;
    },
    getUserData() {
      axios
        .get("https://backend.devroad.site/api/user", {
          headers: {
            jwt: "jwt",
          },
        })
        .then((response) => {
          this.userId = response.data.userId;
          this.userName = response.data.userName;
          this.roadmapId = response.data.roadmapId;
          this.totalSubjectIdx = response.data.totalSubjectIdx;
          this.curSubjectIdx = response.data.curSubjectIdx;
          this.totalChapterIdx = response.data.totalChapterIdx;
          this.curChapterIdx = response.data.curChapterIdx;
          this.setProgressbar();
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    setProgressbar() {
      if (this.totalChapterIdx == 0) {
        this.roadmapPercentage = 0;
        return;
      }
      this.roadmapPercentage = parseInt(
        (this.curChapterIdx / this.totalChapterIdx) * 100
      );
    },
    getSubData: function () {
      var vm = this;
      axios
        .get("https://backend.devroad.site/" + "api/roadmap", {
          headers: {
            jwt: "jwt",
          },
        })
        .then((response) => {
          console.log("user res : " + response);
          vm.isSuccess = response.data.success;
          vm.roadmapDetail = response.data.subjects;
          this.$store.commit("setRoadmapDetailStatus", response.data.subjects);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
