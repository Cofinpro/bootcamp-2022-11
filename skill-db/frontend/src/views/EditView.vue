
<template>
  <v-container v-if="!detailStore.loading">
    <EditComponent update=true :detail="detailStore.details"/>
  </v-container>
</template>

<script lang="ts">
import EditComponent from "@/components/EditComponent.vue";
import {ref} from "vue";
import router from "@/router";
import {useDetailStore} from "@/stores/DetailStore";
import {useRoute} from "vue-router";
import {useErrorStore} from "@/stores/ErrorStore";

export default {
  components: {EditComponent},
  setup() {
    const detailStore = useDetailStore();
    const id = Number(useRoute().params.id);
    detailStore.loadDetailsById(id);
    const errorStore = useErrorStore();
    const locked = ref(false);
    const toDelete = ref(false);

    function toggleDelete(): void {
      toDelete.value = !toDelete.value;
    }

    function lockProfile(): void {
      locked.value = true;
      console.log("This profile is now locked away.");
      /*router.push(`/`);*/
    }

    function deleteProfile(): void {
      detailStore.deleteDetailsByID(id);
      if (!errorStore.hasError) {
        router.push(`/`);
      }
    }

    return {
      detailStore, toDelete, locked, toggleDelete,
      lockProfile, deleteProfile
    };
  },
}
</script>

<style scoped>

</style>
