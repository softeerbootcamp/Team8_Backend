<template>
    <div>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">이름</th>
                    <th scope="col">Email</th>
                    <th scope="col">로드맵 관리</th>

                </tr>
            </thead>
            <tbody>
                <tr v-for="(user, index) in allUserData" :key="index">
                    <th scope="row">{{ index + 1 }}</th>
                    <td>{{ user.userName }}</td>
                    <td>{{ user.email }}</td>
                    <td>
                        <!-- <router-link :to="'/roadmapFactory/' + user" style="text-decoration: none; color:black;"> -->
                        <button>
                            로드맵 관리
                        </button>
                        <!-- </router-link> -->
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import { adminUser } from '@/api'
export default {
    name: "AdminUserView",
    data() {
        return {
            allUserData: [],
            success: false,
        };
    },
    mounted() {
        this.getAllUserDataForAdmin();
    },
    methods: {
        async getAllUserDataForAdmin() {
            const config = {
                headers: {
                    jwt: this.$store.state.jwt
                }
            };

            await adminUser(config)
                .then((response) => {
                    this.success = response.data.success;
                    this.allUserData = response.data.users;
                })
                .catch(function (error) {
                    console.log(error);
                });

        }
    }


}
</script>

<style>

</style>