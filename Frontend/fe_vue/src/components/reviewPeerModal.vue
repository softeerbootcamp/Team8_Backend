<template>
    <transition name="modal" appear>
        <div class="modal-mask">
            <div class="modal-wrapper">
                <div class="modal-container">
                    <div class="modal-header mt-1">
                        peer 모달입니다!!!!
                    </div>
                    <div class="modal-body">
                        <div class="row row-cols-1 row-cols-md-2 g-4 mt-1" style="height:100%">
                            <div class="col">
                                <div class="card" style="height:80%">
                                    <div class="card-header">{{ cards.card1.username }}</div>
                                    <div class="card-body">{{ cards.card1.url }}</div>
                                    <div class="card-body">{{ cards.card1.curSubject }}</div>
                                </div>
                            </div>
                            <div class="col">
                                <div class="card" style="height:80%">
                                    <div class="card-header">{{ cards.card2.username }}</div>
                                    <div class="card-body">{{ cards.card2.url }}</div>
                                    <div class="card-body">{{ cards.card2.curSubject }}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <footer class="modal-footer">
                        <slot name="footer">
                            <div>
                                <button class="btn btn-dark mr-1" style="margin:10px"
                                    @click="$emit('card-selected', 'gobackToSelectFromPeer')">뒤로가기</button>
                                <button class="btn btn-dark" @click="$emit('close')">창닫기</button>

                            </div>
                        </slot>
                    </footer>
                </div>

            </div>
        </div>
    </transition>
</template>
<script>
import { getPeerDetail } from '@/api'
export default {
    name: "reviewPeerModal",
    data() {
        return {
            peerDatas: [],
            cards: {
                card1: {
                    username: "",
                    url: "",
                    curSubject: "",
                },
                card2: {
                    username: "",
                    url: "",
                    curSubject: "",
                }

            },


        };
    },
    mounted() {
        this.getPeerData();
    },
    methods: {
        async getPeerData() {
            console.log("this.$store.state.curSubExamId" + this.$store.state.curSubExamId);
            const config = {
                headers: {
                    jwt: this.$store.state.jwt,
                }
            }
            await getPeerDetail(config, this.$store.state.curSubExamId)
                .then((response) => {
                    if (response.data.success) {
                        this.cards.card1 = response.data.peerDetails[0]
                        this.cards.card2 = response.data.peerDetails[1]
                        // this.peerDatas = response.data.peerDetails
                        // console.log("this.peerDatas : " + this.peerDatas);
                        // this.cards.card1.name = this.peerDatas[0].username;
                        // this.cards.card1.url = this.peerDatas[0].url;
                        // this.cards.card1.curSubject = this.peerDatas[0].curSubject;
                        // this.cards.card2 = this.peerDatas[1];

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