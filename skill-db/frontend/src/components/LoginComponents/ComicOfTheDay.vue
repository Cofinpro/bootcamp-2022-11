<template>
  <v-img
      :max-height="mobile ? '' : '100vh'"
      :width="mobile ? '90vw' : ''"
      :src="comicStore.source"
      :alt="comicStore.description"/>
</template>

<script setup lang="ts">
import {useComicStore} from "@/components/LoginComponents/ComicStore";

const props = defineProps({mobile: Boolean});
const comicStore = useComicStore();
</script>