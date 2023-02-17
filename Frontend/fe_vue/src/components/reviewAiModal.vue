<template>
    <transition name="modal" appear>
        <div class="modal-mask">
            <div class="modal-wrapper">
                <div class="modal-container">
                    <div class="modal-header mt-1">
                        ai 모달입니다!!!!
                    </div>
                    <div class="modal-body">

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
import { postAiCodeReviewModal } from '@/api'
export default {
    name: "reviewAiModal",
    mounted() {
        this.postAiCodeReview();
    },
    methods: {
        async postAiCodeReview() {
            // const params = {
            //     "submissionId": 149
            // }
            const config = {
                headers: {
                    jwt: this.$store.state.jwt
                }
            };
            await postAiCodeReviewModal(config, 149)
                .then((response) => {
                    if (response.data.success) {

                        console.log("이슈 깃헙 성공 ");
                    } else {
                        console.log("데이터를 불러오는데 실패하였습니다!")
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
    }
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
</style>