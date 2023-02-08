<template>
    <div class="d-flex justify-content-center align-items-center" style="height: 80vh;">
        <div class="card text-center" style="width: 500px;">
            <div class="card-header bg-primary text-white">
                기초 프로그래밍 - 과정 수료 시험
            </div>
            <div class="card-body">
                <p class="card-title">본인이 배운 과정으로 로또 프로그램을 만들어 주세요.</p>
                <hr>
                <p class="card-text font-weight-bold">요구사항</p>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">1. 숫자 6개를 입력받고, 1~45 사이의 숫자가 겹치지 않게 들어와야 한다.</li>
                    <li class="list-group-item">2. 랜덤한 로또 번호 6개를 겹치지 않게 생성하여 등수를 판별해라.</li>
                    <li class="list-group-item">3. 등수에 따라 얼마의 돈을 벌었는지 출력해라.</li>
                </ul>
            </div>
            <div class="card-footer">
                <button>제출하러가기</button>
                <!-- <router-link :to="{ name: 'home' }" class="btn btn-secondary">Back</router-link> -->
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: 'ExamView'
}
</script>
<style>

</style>