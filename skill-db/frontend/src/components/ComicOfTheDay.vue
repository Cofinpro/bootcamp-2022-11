<template>
  <v-img
      :cover="!mobile"
      :max-height="mobile ? '' : '100vh'"
      :width="mobile ? '90vw' : ''"
      :src="source"
      :alt="description"
   />
</template>

<script lang="ts">
import {useComicStore} from "@/stores/ComicStore";

export default {
  name: "ComicOfTheDay",
  props: ['source', 'description', 'title', 'mobile'],
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