<template>
  <v-container v-show="!detailStore.loading">
    <EditComponent update=true
                   :detail="detailStore.details"
    :old-picture="detailStore.profilePic"/>
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
    const id = String(useRoute().params.id);
    detailStore.loadDetailsById(id);

    return {
      detailStore
    };
  },
}
</script>

<style scoped>
</style>
