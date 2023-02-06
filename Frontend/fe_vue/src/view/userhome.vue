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
    <!-- <router-link :to="{ name: 'RoadMap' }" v-if="isRoadmapStarted"> -->
    <button @click="getSubData" v-if="!roadMapShowClicked">
      로드맵 시작하기!?
    </button>
  </router-link>
  <router-link :to="{ name: 'ChapterView' }" v-if="isRoadmapStarted">
    <button>로드맵 이어하기!</button>
  </router-link>
  <button type="button" class="btn btn-primary btn-sm">
    <span class="bi bi-file-text"></span>
  </button>
</template>
<script>
import axios from "axios";

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
      totalChapterIdx: 0,
      curChapterIdx: 0,
      jwt: null,
    };
  },
  mounted() {
    this.getJwt();
    this.getUserData();
  },
  computed: {
    isRoadmapStarted() {
      if (this.totalChapterIdx != 0) {
        return true;
      }
      return false;
    },
  },
  methods: {
    getJwt() {
      this.jwt = this.$store.state.jwt;
      console.log("jwt : " + this.jwt);
    },
    async getUserData() {
      try {
        const response = await axios.get("https://backend.devroad.site/api/user", {
          headers: {
            jwt: this.jwt,
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
      if (response.status != 200) {
        console.log(response.data);
      }
      } catch (error) {
                //console.error(error);

      }
      
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
    async getSubData() {
      var vm = this;
      await axios.get("https://backend.devroad.site/" + "api/roadmap", {
          headers: {
            jwt: this.$store.state.jwt,
          },
        })
        .then((response) => {
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

<style></style>
