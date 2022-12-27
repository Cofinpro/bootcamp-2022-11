<template>
  <v-container v-if="! detailStore.loading">
    <h1>Neues Profil erstellen</h1>
    <h2>Hier kannst du ein neues Profil anlegen!</h2>

    <EditComponent update=false />
  </v-container>
  <ErrorSnackbar></ErrorSnackbar>
</template>

<script lang="ts">
import EditComponent from "@/components/EditComponent.vue";
import {useDetailStore} from "@/stores/DetailStore";
import ErrorSnackbar from "@/components/ErrorSnackbar.vue";

export default {
  components: {ErrorSnackbar, EditComponent},
  setup() {
    const detailStore = useDetailStore();
    return {
      detailStore,
    }
  },
}
</script>
