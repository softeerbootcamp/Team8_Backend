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
        chapterId: "",
        chapterName: "",
        chapterUrl: "",
        thumbnailUrl: "",
        description: "",
        finish: true
    }),
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
                        this.chapterName = response.data.chapterName;
                        this.chapterId = response.data.chapterId;
                        this.chapterUrl = response.data.chapterUrl;
                        this.thumbnailUrl = response.data.thumbnailUrl;
                        this.description = response.data.description;
                        this.finish = response.data.finish;
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