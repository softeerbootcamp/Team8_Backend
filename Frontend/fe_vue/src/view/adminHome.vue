<template>
    <div>
        유저 요청 관리 페이지
    </div>
    <div>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Email</th>
                    <th scope="col">작성하러 가기!</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(user, index) in noRoadmapUsers" :key="index">
                    <th scope="row">{{ index + 1 }}</th>
                    <td>{{ user.email }}</td>
                    <td>
                        <router-link :to="'/roadmapFactory/' + user.email">
                            <button>
                                유저 로드맵 작성하기
                            </button>
                        </router-link>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
<script>
import { getNoRoadmapUserData } from '@/api';
export default {
    name: "AdminHome",
    data() {
        return {
            noRoadmapUsers: [],
        }
    },
    mounted() {
        this.getNoRoadMapUserDetail();
    },
    methods: {
        async getNoRoadMapUserDetail() {
            const config = {
                headers: {
                    jwt: this.$store.state.jwt
                }
            };
            await getNoRoadmapUserData(config)
                .then((response) => {
                    this.noRoadmapUsers = response.data.users;
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
    },
}
</script>

<style>

</style>