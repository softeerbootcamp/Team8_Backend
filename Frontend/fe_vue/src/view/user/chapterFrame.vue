<template>
    <iframe :src="chapterUrl" title="YouTube video player" frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" width="560"
        height="315" allowfullscreen />

</template>
<script>
import { getChapterDetailData } from '@/api';
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
                        this.chapterUrl = this.baseUrl + response.data.chapterDetail.chapterUrl;
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