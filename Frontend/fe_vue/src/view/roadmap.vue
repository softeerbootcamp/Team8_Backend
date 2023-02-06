<template>
  <div class="d-grid gap-2 col-6 mx-auto">
    <div v-for="subject in getRoadmapDetailFromStore()" :key="subject">
      <span v-for="course in subject" :key="course">
        <button
          class="btn btn-primary ms-3"
          @click="
            $router.push({
              name: 'CourseView',
              params: { subject: subject },
            })
          "
        >
          {{ course[0] }}
        </button>
      </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "RoadMap",

  data() {
    return {
      isCardOn: false,
      success: false,
      subDataSuccess: false,

      subjectDetail: [],
    };
  },
  methods: {
    getRoadmapDetailFromStore() {
      return this.$store.state.roadmapDetail;
    },
  },
};
</script>

<style></style>
