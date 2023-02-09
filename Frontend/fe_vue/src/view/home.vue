<template>
  <div>
    홈화면
  </div>
  <div>
    <div class="d-flex justify-content-center">
      <div class="highway d-flex">
        <img class="road w-100 h-100" src="../assets/images/road.png" />
        <div ref="car" class="car d-flex align-items-center justify-content-center">
          <img class="car" src="../assets/images/porsche.svg" />
        </div>
        <div class="sign d-flex align-items-center justify-content-center speed-limit">
          <img class="sign" src="../assets/images/sign.png" />
        </div>
        <div class="sign d-flex align-items-center justify-content-center speed-limit">
          <img class="sign" src="../assets/images/css-3.png" />
        </div>
        <div class="sign d-flex align-items-center justify-content-center speed-limit">
          <img class="sign" src="../assets/images/html-5.png" />
        </div>
        <div class="sign d-flex align-items-center justify-content-center speed-limit">
          <img class="sign" src="../assets/images/python.png" />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "Home",
  data() {
    return {
      carPosition: 0,
      signPositions: [0, 40, 80, 120]

    };
  },
  mounted() {
    this.moveCar();
  },
  methods: {
    moveCar() {
      this.carPosition += 10;
      this.$refs.car.style.left = `${this.carPosition}px`;
      if (this.carPosition >= window.innerWidth) {
        this.carPosition = -50;
      }
      requestAnimationFrame(this.moveCar);
    }
  },
  computed: {}
};
</script>
<style>
.highway {
  width: 80%;
  height: 200px;
  position: relative;
  overflow: hidden;
}

.car {
  width: 50%;
  height: 80%;
  position: absolute;
  bottom: 0;
  left: 0;
}

.sign {
  width: 30%;
  height: 50%;
  position: absolute;
  top: 50%;
  right: 0;
  transform: translateY(-50%);
}
</style>