<template>
    <transition name="modal" appear>
        <div class="modal-mask">
            <div class="modal-wrapper">
                <div class="modal-container">
                    <div class="modal-header mt-1">
                        테스트를 통과하셨군요! 이후 과정을 어떻게 진행하시겠어요?
                    </div>
                    <div class="modal-body">
                        <div class="row row-cols-1 row-cols-md-2 g-4 mt-1" style="height:100%">
                            <div class="col">
                                <div class="card" style="height:80%">
                                    <div class="card-body">
                                        <h5 class="card-title">AI 와 함께하는 리뷰</h5>
                                        <p class="card-text">최신 AI 와 함께 틀린 부분을 논의해 보아요!</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col">
                                <div class="card" style="height:80%">
                                    <div class="card-body">
                                        <h5 class="card-title">Peer 리뷰</h5>
                                        <p class="card-text">같은 과목을 들은 학생들과 서로 리뷰해 보아요!
                                        </p>
                                        <p class="card-text">리뷰를 진행하면 포인트를 모을 수 있습니다!</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <footer class="modal-footer">
                        <slot name="footer">
                            <button class="btn btn-dark" @click="$emit('close')">Close</button>
                        </slot>
                    </footer>
                </div>

            </div>
        </div>
    </transition>
</template>
<script>

export default {

}
</script>

<style>
.modal-mask {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: table;
    transition: opacity 0.3s ease;

}

.modal-wrapper {
    display: table-cell;
    vertical-align: middle;

}

.modal-container {
    width: 800px;
    height: 400px;
    margin: 0px auto;
    padding: 20px 30px;
    background-color: #fff;
    border-radius: 2px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
    transition: all 0.3s ease;

}

.modal-header h3 {
    margin-top: 0;
    color: #43b983;

}

.modal-body {
    height: 280px;

}

.modal-enter {
    opacity: 0;

}

.modal-leave-active {
    opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
    -webkit-transform: scale(1.1);
    transform: scale(1.1);
}

.modal-body,
.modal {
    color: #666 !important;
}

/* .modal {
  &.modal-overlay {
    display: flex;
    align-items: center;
    justify-content: center;
    position: fixed;
    z-index: 30;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
  }

  &-window {
    background: #fff;
    border-radius: 4px;
    overflow: hidden;
  }

  &-content {
    padding: 10px 20px;
  }

  &-footer {
    background: #ccc;
    padding: 10px;
    text-align: right;
  }
}

// 오버레이 트랜지션
.modal-enter-active, .modal-leave-active {
  transition: opacity 0.4s;

  // 오버레이에 포함되어 있는 모달 윈도의 트랜지션
  .modal-window {
    transition: opacity 0.4s, transform 0.4s;
  }
}

// 딜레이가 적용된 모달 윈도가 제거된 후에 오버레이가 사라짐
.modal-leave-active {
  transition: opacity 0.6s ease 0.4s;
}

.modal-enter, .modal-leave-to {
  opacity: 0;

  .modal-window {
    opacity: 0;
    transform: translateY(-20px);
  }
} */
</style>