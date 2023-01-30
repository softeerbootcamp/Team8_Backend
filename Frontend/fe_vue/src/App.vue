<template>
  <div class="black-bg" v-if="모달창열렸니 == true">
    <div class="white-bg">
      <h4>
        상세페이지임
      </h4>
      <button @click="모달창열렸니 = false">
        닫기
      </button>
      <p>
        상세페이지 내용임
      </p>
    </div>
  </div>

  <div class="menu">
    <a v-for="hi in 메뉴들" :key="hi">{{ hi }}</a>
  </div>
  <div>
    <h4 @click="모달창열렸니 = true">{{ products[0] }} </h4><p>50만원</p>

    <button @mouseover="신고수++"> 허위매물 신고</button> <span>신고 수 : {{ 신고수 }}</span>

  </div>

</template>

<script>
import data from './assets/oneroom.js'
export default {
  name: 'App',
  data() {
    return {
      원룸들 : data,
      모달창열렸니:false,
      신고수: 0,
      메뉴들: ["Home", "Shop", "About"],
      products: ['역삼 원룸', '천호 원룸 ', '마포구 원룸']
    }
  },
  methods: {

  },
  components: {

  }
}
</script>

<style>
body{
  margin:0
}
div{
  box-sizing: border-box;
}
.black-bg{
  width: 100%; height: 100%;
  background: rgba(0, 0, 0, 0.5);
  position: fixed; padding: 20px;
}
.white-bg{
  width: 100%; background: white;
  border-radius: 8px;
  padding: 20px;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

.menu {
  background: darkslateblue;
  padding: 15px;
  border-radius: 5px;

}

.menu a {
  color: white;
  padding: 10px;
}

.room_image {
  width: 100%;
}
</style>
