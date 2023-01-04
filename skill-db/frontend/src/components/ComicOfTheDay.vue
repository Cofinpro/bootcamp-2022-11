<template>
  <v-img
      cover
      max-height="100vh"
      :src="source"
      :alt="description"
   />
</template>

<script>
import {useComicStore} from "@/stores/ComicStore";

export default {
  name: "ComicOfTheDay",
  props: ['source', 'description', 'title'],
  setup() {
    const comicStore = useComicStore();
    comicStore.loadComicOfTheDay();
    return {
      comicStore
    }
  },
}
</script>

<style scoped>

</style>