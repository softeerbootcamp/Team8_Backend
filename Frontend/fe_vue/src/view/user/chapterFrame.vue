<template>
    <iframe :src="src" title="YouTube video player" frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" width="560"
        height="315" allowfullscreen />

</template>
<script>
export default {
    name: 'ChapterFrame',
    data: () => ({
        myIframe: null,
        src: "https://www.youtube.com/embed/dh4hdtZ00EU"
    }),
    methods: {
        onLoad(frame) {
            this.myIframe = frame.contentWindow
        }
    }
}
</script>

<style>

</style>