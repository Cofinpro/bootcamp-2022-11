<template>
  <v-container v-if="! detailStore.loading">
    <h1>Neues Profil erstellen</h1>
    <h2>Hier kannst du ein neues Profil anlegen!</h2>

    <EditComponent update=false />
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