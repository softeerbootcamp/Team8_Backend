<template>
    <div class="container">
        <div class="roadmapGuide">
            <div @click="guideOnClick" style="color:#B01E68; text-decoration: underline;">
                Roadmap Guide*
            </div>
            <div class="row row-cols-auto mt-2" v-if="guideOn">
                <div class="col">
                    <button class="btn redbtn">
                        강의주제
                    </button>
                </div>
                <div class="col">
                    <button class="btn passed">
                        시험통과
                    </button>
                </div>
                <div class="col">
                    <button class="btn purchased">
                        시험가능
                    </button>
                </div>
                <div class="col">
                    <button class="btn none" style="color:black !important">
                        구독필요
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>
export default {
    name: 'RoadmapGuide',
    data() {
        return {
            guideOn: false,
        }
    },
    methods: {
        guideOnClick() {
            this.guideOn = !this.guideOn;
        }
    }
}
</script>
  
<style>
/* case 'NONE':
          return 'btn btn-light btn-sm';
        case 'PURCHASED':
          return 'btn btn-info btn-sm';
        case 'SUBMITTED':
          return 'btn btn-warning btn-sm';
        case 'PASSED':
          return 'btn btn-success btn-sm';
        case 'FAILED':
          return 'btn btn-danger btn-sm';
        default:
          return 'btn btn-light btn-sm'; */
.roadmapGuide {
    position: fixed;
    left: 20%;
    top: 12%;
    width: 20%;

}

.btn.redbtn {
    background-color: #B01E68 !important;
    color: white !important;
    margin-bottom: 5px;
}

.btn.passed {
    background-color: #5cb85c !important;
    color: white !important;
    margin-bottom: 5px;
}

.btn.none {
    background-color: white !important;
    color: white !important;
    margin-bottom: 5px;
}

.btn.purchased {
    background-color: #5bc0de !important;
    color: white !important;
    margin-bottom: 5px;
}
</style>
  