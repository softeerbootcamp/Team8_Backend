<template>
  <div class="d-flex justify-content-center mt-4" style="height: 80vh;overflow-y: scroll;">
    <div class="card-deck">
      <div v-for="chapter in chapters" :key="chapter">
        <router-link :to="{ name: 'ChapterFrame', params: { chapterId: chapter.chapterId } }"
          style="text-decoration: none; color: black;">
          <div class="card text-center mb-4">
            <div class="card-body">
              <h5 class="card-title">
                <div>
                  <div class="form-check">
                    <input class="form-check-input" type="checkbox" :checked="chapter.finish" value=""
                      id="flexCheckDefault">
                    {{ chapter.chapterName }}

                  </div>
                </div>
              </h5>
              <p class="card-text">{{ chapter.description }}</p>
            </div>
          </div>
        </router-link>

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
      chapters: [],
      curChapterId: "",
      isChecked: false,
      /* chapters
         "chapterName" : "자료형", 
         "chapterId" : 10011, 
         "chapterUrl" : "http://toa/lec/10011", 
         "thumbnailUrl":"url", 
         "description" : "자료형의 종류는 무엇이 있을까용?",
         "finish" : true*/
    };
  },
  computed: {
    courseId() {
      return this.$route.params.courseId;
    },
  },
  mounted() {
    this.getchapters();
  },
  methods: {
    async getchapters() {
      this.curCourseId = this.$route.params.courseId;

      const config = {
        headers: {
          jwt: this.$store.state.jwt
        }
      };
      await getCourseData(this.curCourseId, config)
        .then((response) => {
          this.chapters = response.data.chapters;
          this.curChapterId = response.data.curChapterId;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
};
</script>
