<template>
  <h1 class="homeTitle center">
    본인만의 커리어를 만들어보세요!<br>


  </h1>
  <div class="d-flex justify-content-center">
    <div class="highway d-flex align-items-center">
      <img class="road w-50 h-100" src="../assets/images/road.svg" />
      <img class="road w-50 h-100" src="../assets/images/road.svg" />

      <div ref="car" class="car d-flex align-items-center justify-content-center">
        <img class="car" src="../assets/images/porsche.svg" />
      </div>
      <div ref="car2" class="car2 d-flex align-items-center justify-content-center">
        <img class="car2" src="../assets/images/porsche2.svg" />
      </div>
      <div class="sign_1 d-flex align-items-center justify-content-center speed-limit">
        <img class="sign_1" src="../assets/images/spring.svg" />
      </div>
      <div class="sign_2 d-flex align-items-center justify-content-center speed-limit">
        <img class="sign_2" src="../assets/images/bootstrap.svg" />
      </div>
      <div class="sign_3 d-flex align-items-center justify-content-center speed-limit">
        <img class="sign_3" src="../assets/images/vueSvg.svg" />
      </div>
      <div class="sign_4 d-flex align-items-center justify-content-center speed-limit">
        <img class="sign_4" src="../assets/images/tensorflow.svg" />
      </div>
    </div>
  </div>
  <h3>
    <button type="button" class="btn btn-dark d-grid gap-2 col-1 mt-4 mx-auto" @click="startBtnClickEvent">출발!</button>
  </h3>
</template>
<script>
export default {
  name: "Home",
  data() {
    return {
      carPosition: 0,
      car2Position: window.innerWidth,
      signPositions: [0, 40, 80, 120]
    };
  },
  mounted() {
    this.moveCar();
    this.moveCar2();
  },
  methods: {
    moveCar() {
      if (this.$refs.car) {
        this.carPosition += 5;
        this.$refs.car.style.left = `${this.carPosition}px`;
        if (this.carPosition >= window.innerWidth) {
          this.carPosition = -50;
        }
        requestAnimationFrame(this.moveCar);
      }
    },
    moveCar2() {
      if (this.$refs.car2) {
        this.car2Position -= 7;
        this.$refs.car2.style.left = `${this.car2Position}px`;
        if (this.car2Position <= -50) {
          this.car2Position = window.innerWidth;
        }
        requestAnimationFrame(this.moveCar2);
      }
    },
    startBtnClickEvent() {
      if (!this.$store.state.isLogin) {
        this.$router.push('/login');
      } else {
        if (this.$store.state.isRoadmapExist) {
          this.$router.push('/roadmap');

        } else {
          this.$router.push('/userPendingView');
        }
      }
    }
  },
  computed: {}
};
</script>
<style>
.homeTitle {
  padding-top: 150px;
  text-align: center;

}


.highway {
  width: 100%;
  height: 200px;
  position: relative;
  overflow: hidden;
}

.car {
  width: 80px;
  height: 80%;
  position: absolute;
  bottom: 0;
  left: 0;
}

.car2 {
  width: 80px;
  height: 80%;
  position: absolute;
  bottom: 0;
  left: 0;
  top: 4%;
}

.sign_1 {
  width: 30%;
  height: 45%;
  position: absolute;
  top: 41%;
  right: 0%;
  transform: translateY(-50%);
}

.sign_2 {
  width: 30%;
  height: 45%;
  position: absolute;
  top: 60%;
  right: 20%;
  transform: translateY(-50%);
}

.sign_3 {
  width: 30%;
  height: 45%;
  position: absolute;
  top: 41%;
  right: 40%;
  transform: translateY(-50%);
}

.sign_4 {
  width: 30%;
  height: 45%;
  position: absolute;
  top: 60%;
  right: 60%;
  transform: translateY(-50%);
}
</style>