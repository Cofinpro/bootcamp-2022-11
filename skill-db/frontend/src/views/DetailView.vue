<template>
  <div v-if="!detailStore.loading">
    <SettingsButton :id="id"
                    :edit="false"/>
    <DetailComponent :detailStore="detailStore"/>
  </div>
</template>

<script lang="ts">
import {useDetailStore} from "@/stores/DetailStore";
import {useRoute} from "vue-router";
import SettingsButton from "@/components/SettingsButton.vue";
import DetailComponent from "@/components/DetailComponent.vue";

export default {
  name: "DetailView",
  components: { SettingsButton, DetailComponent},
  setup() {
    const detailStore = useDetailStore();
    const id = Number(useRoute().params.id);
    detailStore.loadDetailsById(id);

    return {
      detailStore, id
    };
  },
}
</script>

<style scoped>
</style>
