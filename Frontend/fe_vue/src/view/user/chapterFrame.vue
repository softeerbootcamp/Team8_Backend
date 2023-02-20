<template>
    <div class="d-flex justify-content-center mt-4">

        <iframe :src="chapterUrl" title="YouTube video player" frameborder="0"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" width="560"
            height="315" allowfullscreen style="width:100vh; height: 50vh;" />
    </div>
    <div class="d-flex justify-content-center mt-4">
        <button class="btn btn-dark" @click="finishChapter" onmouseover="this.innerHTML='다음 강의로';"
            onmouseout="this.innerHTML='수강완료';" style="margin-right:10px !important">수강완료</button>
        <button class="btn btn-dark"
            @click="$router.push({ name: 'chapterView', params: { courseId: courseId } })">뒤로가기</button>
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
        finish: true,
        nextChapterId: "",
        courseId: "",

    }),
    mounted() {
        this.getChapterDetail(this.$route.params.chapterId);
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
                        this.nextChapterId = response.data.nextChapterId;
                        this.getChapterDetail(this.nextChapterId);

                    }
                })
                .catch(function (error) {
                    console.log(error);
                });

        },
        refreshAll() {
            this.$router.go(0);
        }
        ,
        onLoad(frame) {
            this.myIframe = frame.contentWindow
        },
        async getChapterDetail(chapterId) {
            this.chapterId = chapterId;

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
                        this.courseId = response.data.chapterDetail.courseId;


                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        },

    }
}
</script>

<style></style>