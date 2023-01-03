<template>
  <v-container v-if="!detailStore.loading">
    <EditComponent update=true
                   :detail="detailStore.details"/>
  </v-container>
</template>

<script lang="ts">
import EditComponent from "@/components/EditComponent.vue";
import {useDetailStore} from "@/stores/DetailStore";
import {useRoute} from "vue-router";

export default {
  name: "EditView",
  components: { EditComponent },
  setup() {
    const detailStore = useDetailStore();
    const id = Number(useRoute().params.id);
    detailStore.loadDetailsById(id);

    return {
      detailStore
    };
  },
}
</script>

<style scoped>
</style>