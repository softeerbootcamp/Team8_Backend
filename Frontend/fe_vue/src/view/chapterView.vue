<template>
  <div class="row row-cols-1 row-cols-md-3 g-4">
    <div class="col" v-for="chapter in courseDetail" :key="chapter">
      <div class="card">
        <div class="card-body">
          <h5 class="card-title">{{ chapter.chapterName }}</h5>
          <p class="card-text">{{ chapter.explain }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getCourseData } from "@/api";


export default {
  name: "ChapterView",
  data() {
    return {
      curCourseId: "",
      courseDetail: [],
      curChapterId: "",
    };
  },
  computed: {
    courseId() {
      return this.$route.params.courseId;
    },
  },
  mounted() {
    this.getCourseDetail();
  },
  methods: {
    async getCourseDetail() {
      this.curCourseId = this.$route.params.courseId;

      const config = {
        headers: {
          jwt: this.$store.state.jwt
        }
      };
      await getCourseData(this.curCourseId, config)
        .then((response) => {
          this.courseDetail = response.data.chapters;
          this.curChapterId = response.data.curChapterId;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>
