<template>
<div class = "container preRoadMap">
    <div class="row justify-content-center">
        <div class="col col-8 d-flexm mt-4 justify-content-center text-center">
            <h1 r>로드맵을 만드는 중입니다.</h1>
        </div>
        <div class="col col-8 d-flexm mt-4 center">
            <img :src="getImageSrc()" class="offset-3 col-6">
        </div>
        <div class="col col-8 d-flexm mt-4 text-center">
            <p>DevRoad는 수동으로 맞춤형 로드맵을 만들어 드리고 있어요!</p>
            <p>사전질문지를 아직 작성안하셨다면 작성해주세요</p>
        </div>

    <div class="d-flex justify-content-center mt-4">
        <div class="col col-4 d-flex justify-content-between">
            <button class="col-4 btn btn-secondary ml-5" @click="this.$router.push({ name: 'Home' })">돌아가기</button>
            <button class="col-4 btn btn-primary ml-5" @click="this.$router.push({ name: 'SurveyForm' })">작성하러 가기</button>
        </div>
    </div>
    </div>
</div>
</template>

<script>
export default {
    name: "UserPendingView",
    methods: {
    getImageSrc() {
      return require('@/assets/making.png')
    }
  }

}
</script>

<style>

.container.preRoadMap {
    background-color: wheat;
    margin: auto !important;
    width: 70vw !important;
    height: 80% !important;
    overflow-y: scroll;
    border-radius: 40px 40px;
}

.btn-primary, .btn-primary:hover, .btn-primary:active, .btn-primary:visited {
    background-color: #DC3535 !important;
    border: #B01E68 !important;
}

</style>