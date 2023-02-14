<template>
  <div class="container mt-5">
    <div class="row">
      <div class="col-md-3 col-sm-6">
        강의 진행도
      </div>
    </div>
    <div class="row">
      <div class="col-md-3 col-sm-6">
        <div class="progress blue">
          <span class="progress-left">
            <span class="progress-bar"></span>
          </span>
          <span class="progress-right">
            <span class="progress-bar"></span>
          </span>
          <div class="progress-value">{{ roadmapPercentage }}%</div>
        </div>
      </div>
    </div>
  </div>
  <router-link :to="{ name: 'RoadMap' }" v-if="!isRoadmapStarted" style="text-decoration: none;">
    <button class="btn btn-dark d-grid gap-2 col-2 mx-auto mt-4" style=" color:white;" v-if="!roadMapShowClicked">
      로드맵 시작하기!?
    </button>
  </router-link>
  <div v-if="isRoadmapStarted">
    <button @click="routeByCurChapterPK" class="btn btn-dark d-grid gap-2 col-2 mx-auto mt-4">로드맵 이어하기!</button>
  </div>
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
      roadmapId: "-1",
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
    routeByCurChapterPK() {
      var cpk = this.curChapterPK;
      if (cpk === -1) {
        this.$router.push('/roadmap');
      } else {
        this.$router.push({ name: 'ChapterFrame', params: { chapterId: this.curChapterPK } })
      }
    },
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
            this.$store.commit('setIsRoadmap', true);
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
      if (this.chapterPercent === 0) {
        this.roadmapPercentage = 0;
      } else {
        this.roadmapPercentage = parseInt(this.chapterPercent * 100);
      }
    },

  },
};
</script>

<style>
.progress {
  width: 150px;
  height: 150px !important;
  line-height: 150px;
  background: none;
  margin: 0 auto;
  box-shadow: none;
  position: relative;
  --bs-progress-bg: #ffffff !important;
}

.progress:after {
  content: "";
  width: 100%;
  height: 100%;
  border-radius: 50%;
  border: 12px solid #fff;
  position: absolute;
  top: 0;
  left: 0;
}

.progress>span {
  width: 50%;
  height: 100%;
  overflow: hidden;
  position: absolute;
  top: 0;
  z-index: 1;
}

.progress .progress-left {
  left: 0;
}

.progress .progress-bar {
  width: 100%;
  height: 100%;
  background: none;
  border-width: 12px;
  border-style: solid;
  position: absolute;
  top: 0;
}

.progress .progress-left .progress-bar {
  left: 100%;
  border-top-right-radius: 80px;
  border-bottom-right-radius: 80px;
  border-left: 0;
  -webkit-transform-origin: center left;
  transform-origin: center left;
}

.progress .progress-right {
  right: 0;

}

.progress .progress-right .progress-bar {
  left: -100%;
  border-top-left-radius: 80px;
  border-bottom-left-radius: 80px;
  border-right: 0;
  -webkit-transform-origin: center right;
  transform-origin: center right;
  animation: loading-1 1.8s linear forwards;
}

.progress .progress-value {
  width: 90%;
  height: 90%;
  border-radius: 50%;
  background: #ffffff;
  font-size: 24px;
  color: #d10b4f;
  line-height: 135px;
  text-align: center;
  position: absolute;
  top: 5%;
  left: 5%;
}

.progress.blue .progress-bar {
  border-color: #d10b4f;
}

.progress.blue .progress-left .progress-bar {
  animation: loading-2 1.5s linear forwards 1.8s;
}






@keyframes loading-1 {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }

  100% {
    -webkit-transform: rotate(180deg);
    transform: rotate(180deg);
  }
}

@keyframes loading-2 {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }

  100% {
    -webkit-transform: rotate(180deg);
    transform: rotate(180deg);
  }
}

@keyframes loading-3 {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }

  100% {
    -webkit-transform: rotate(90deg);
    transform: rotate(90deg);
  }
}

@keyframes loading-4 {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }

  100% {
    -webkit-transform: rotate(36deg);
    transform: rotate(36deg);
  }
}

@keyframes loading-5 {
  0% {
    -webkit-transform: rotate(0deg);
    transform: rotate(0deg);
  }

  100% {
    -webkit-transform: rotate(126deg);
    transform: rotate(126deg);
  }
}

@media only screen and (max-width: 990px) {
  .progress {
    margin-bottom: 20px;
  }
}
</style>
