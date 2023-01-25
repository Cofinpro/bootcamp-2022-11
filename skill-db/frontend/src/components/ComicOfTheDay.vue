<template>
  <v-img
      :cover="!mobile"
      :max-height="mobile ? '' : '100vh'"
      :width="mobile ? '90vw' : ''"
      :src="source"
      :alt="description"/>
</template>

<script setup lang="ts">
import {useComicStore} from "@/stores/ComicStore";

const props = defineProps({
  source: String, description: String, title: String, mobile: Boolean
});
const comicStore = useComicStore();
comicStore.loadComicOfTheDay();
</script>