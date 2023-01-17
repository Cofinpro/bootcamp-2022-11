<template>
  <v-container v-show="! detailStore.loading">
    <h1>Neues Profil</h1>

    <EditComponent :update=false />
  </v-container>
</template>

<script lang="ts">
import EditComponent from "@/components/EditComponent.vue";
import {useDetailStore} from "@/stores/DetailStore";

export default {
  name: "NewView",
  components: { EditComponent },
  setup() {
    const detailStore = useDetailStore();
    return {
      detailStore,
    }
  },
}
</script>

<style scoped>
</style>
