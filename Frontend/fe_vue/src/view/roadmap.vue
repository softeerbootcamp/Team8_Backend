<template>
  <div class="d-grid gap-2 col-6 mx-auto">
    <div v-for="subject in getsubjects" :key="subject">
      <!-- subject안에는 여러 course들이 존재한다 -->
      <span v-for="course in subject" :key="course">
        <button class="btn btn-primary ms-3" @click="
          $router.push({
            name: 'CourseView',
            params: { subject: subject },
          })
        ">
          {{ course[0] }}
        </button>
        <button type="button" class="btn btn-primary btn-sm">
          <span class="bi bi-file-text"></span>
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
    };
  },

  computed: {
    getsubjects() {
      console.log("this.$store.state.subjects!!!!! : " + this.$store.state.subjects[1])
      return this.$store.state.subjects;
    }
  }
};
</script>

<style>

</style>
