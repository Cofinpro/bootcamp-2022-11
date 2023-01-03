<template>
  <div v-if="!detailStore.loading">
    <div class="d-flex justify-end">
      <DropdownButton :functions="dropdownFunctions"/>
      <DeleteProfileDialog v-model="toDelete" :functions="dialogFunctions"/>
      <v-overlay v-model="locked" absolute/>
    </div>
    <DetailComponent :detailStore="detailStore"/>
  </div>
</template>

<script lang="ts">
import {useDetailStore} from "@/stores/DetailStore";
import {ref} from "vue";
import router from "@/router";
import {useRoute} from "vue-router";
import DropdownButton from "@/components/DropdownButton.vue";
import DeleteProfileDialog from "@/views/DeleteProfileDialog.vue";

export default {
  name: "DetailView",
  components: { DeleteProfileDialog, DropdownButton },
  setup() {
    const detailStore = useDetailStore();
    const id = Number(useRoute().params.id);
    detailStore.loadDetailsById(id);
    const detail = detailStore.details;

    const locked = ref(false);
    const toDelete = ref(false);

    function enterEdit(): void {
      router.push({name: 'editView', params: {id: id}});
    }

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
      router.push(`/`);
    }

    return {
      dropdownFunctions: [
        {name: 'Bearbeiten', method: enterEdit},
        {name: 'Löschen', method: toggleDelete},
        {name: 'Sperren', method: lockProfile},
      ],
      dialogFunctions: [
        {name: 'Ja', method: deleteProfile},
        {name: 'Nein', method: toggleDelete}
      ],
      detail, detailStore, toDelete, locked
    };
  },
}
</script>

<style scoped>
</style>
