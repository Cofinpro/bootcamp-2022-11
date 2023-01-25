<template>
  <v-container v-show="! detailStore.loading">
    <h1>Neues Profil</h1>

    <EditComponent :update=false />
  </v-container>
</template>

<script setup lang="ts" >
import EditComponent from "@/components/EditComponents/EditComponent.vue";
import {useDetailStore} from "@/stores/DetailStore";

const name = "NewView";
const detailStore = useDetailStore();
</script>

<style scoped>
</style>
