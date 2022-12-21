<template>
  <v-container>


    <EditComponent update=true :detail="detail"/>
  </v-container>
</template>

<script lang="ts">
import EditComponent from "@/components/EditComponent.vue";
import {ref} from "vue";
import router from "@/router";
import {useDetailStore} from "@/stores/DetailStore";

export default {
  components: {EditComponent},
  setup() {
    const detailStore = useDetailStore();
    detailStore.loadDemoDetails();
    /*detailStore.loadDetailsById();*/
    const detail = detailStore.details;

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
      /*detailStore.deleteDetailsByID(detail.getId());*/
      router.push(`/`);
    }

    return {
      detail, toDelete, locked, toggleDelete,
      lockProfile, deleteProfile
    };
  },
}
</script>

<style scoped>

</style>