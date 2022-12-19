<template>
  <v-container>
    <h1>Neues Profil erstellen</h1>
    <h2>Hier kannst du ein neues Profil anlegen!</h2>

    <EditComponent :detail="detail" :detail-store="detailStore" update=true />
  </v-container>
</template>

<script lang="ts">

import {useDetailStore} from "@/stores/DetailStore";
import EditComponent from "@/components/EditComponent.vue";

export default {
  components: {EditComponent},
  setup() {
    const detailStore = useDetailStore();
    detailStore.loadDemoDetails();
    const detail = detailStore.details;

    return {
      detail, detailStore
    }
  }
}

</script>