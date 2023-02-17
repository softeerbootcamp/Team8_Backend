<template>
    <div class="d-flex justify-content-center mt-4">

        <iframe :src="chapterUrl" title="YouTube video player" frameborder="0"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" width="560"
            height="315" allowfullscreen style="width:100vh; height: 50vh;" />
    </div>
    <div class="d-flex justify-content-center mt-4">
        <button class="btn btn-dark" @click="finishChapter">수강완료</button>
    </div>


</template>
<script>
import { getChapterDetailData, putFinishChapter } from '@/api';
export default {
    name: 'ChapterFrame',
    data: () => ({
        myIframe: null,
        baseUrl: "https://youtube.com/embed/",
        chapterId: "",
        chapterName: "",
        chapterUrl: "",
        thumbnailUrl: "",
        description: "",
        finish: true
    }),
    mounted() {
        this.getChapterDetail();
    },
    methods: {
        async finishChapter() {
            const config = {
                headers: {
                    jwt: this.$store.state.jwt
                }
            };
            await putFinishChapter(config, this.chapterId)
                .then((response) => {
                    if (response.data.success) {
                        //                     "nextChapterId" : "10014",
                        // "isCourseFinished" : "false"
                        this.$router.push('/roadmap');
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });

        },

        onLoad(frame) {
            this.myIframe = frame.contentWindow
        },
        async getChapterDetail() {
            this.chapterId = this.$route.params.chapterId;

            const config = {
                headers: {
                    jwt: this.$store.state.jwt
                }
            };
            await getChapterDetailData(config, this.chapterId)
                .then((response) => {
                    if (response.data.success) {
                        this.chapterName = response.data.chapterDetail.chapterName;
                        this.chapterId = response.data.chapterDetail.chapterId;
                        this.chapterUrl = this.baseUrl + response.data.chapterDetail.chapterUrl.split('?v=')[1];
                        console.log("this.chapterurl" + this.chapterUrl);
                        this.thumbnailUrl = response.data.chapterDetail.thumbnailUrl;
                        this.description = response.data.chapterDetail.description;
                        this.finish = response.data.chapterDetail.finish;

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

</style>