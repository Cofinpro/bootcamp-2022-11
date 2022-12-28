<template>
  <v-container>
    <h1>Neues Profil erstellen</h1>
    <h2>Hier kannst du ein neues Profil anlegen!</h2>

    <EditComponent update=false />
  </v-container>
</template>

<script lang="ts">
import EditComponent from "@/components/EditComponent.vue";

export default {
  components: { EditComponent },
}
</script>