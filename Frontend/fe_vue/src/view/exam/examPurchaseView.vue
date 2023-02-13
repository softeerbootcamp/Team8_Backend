<template>
  <div>
    <button id="payment-button" @click="loadToss">15,000원 결제하기</button>
  </div>
</template>

<script>
import { loadTossPayments } from '@tosspayments/payment-sdk'
export default {
  name: "ExamPurchaseView",
  data() {
    return {
      clientKey: 'test_ck_N5OWRapdA8d6BZvDzDPVo1zEqZKL',
      tossPayments: null,
      backendURL: "https://backend.devroad.site",
    };
  },
  methods: {
    sendOrderToToss(accountId, examId, amount) {
      let randomStr = ([1e7] + 1e3 + 4e3 + 8e3 + 1e11).replace(/[018]/g, c =>
        (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
      );
      let payMentBody = {
        amount: amount,
        orderId: accountId + "_" + examId + "_" + randomStr,
        orderName: examId,
        customerName: accountId,
        successUrl: this.backendURL + '/api/purchase/exam/success',
        failUrl: this.backendURL + '/api/purchase/exam/fail',
      };
      return payMentBody;
    },
    tossPaymentRequest() {

    },
    async loadToss() {
      const tossPayment = await loadTossPayments(this.clientKey);
      // TODO : 13, 27, 16000이 아니라 각각에 대한 정보를 가져올것.
      // 필요하면 백엔드에서 jwt, examId로 다 가져올수 있도록 제공가능함.
      tossPayment.requestPayment('카드', this.sendOrderToToss(this.$store.state.accountId, this.$route.params.examId, 16000));
    }

  }
}
</script>

<style>

</style>