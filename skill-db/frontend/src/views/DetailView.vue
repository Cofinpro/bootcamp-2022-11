<template>
  <div v-if="!detailStore.loading">
    <DetailComponent/>
  </div>
</template>

<script lang="ts">
import DetailComponent from "@/components/DetailComponent.vue";
import {useDetailStore} from "@/stores/DetailStore";

export default {
  name: "DetailView",
  components: { DetailComponent },
  setup() {
    const detailStore = useDetailStore();
    return {
      detailStore
    }
  }
}
</script>

<style scoped>
</style>
