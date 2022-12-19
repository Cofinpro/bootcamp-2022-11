<template>
  <v-container>
    <v-menu :close-on-content-click="false">
      <template v-slot:activator="{ props }">
        <v-btn v-bind="props"
               min-width="40px" width="40px" height="35px"
               class="pa-0" elevation="0">
          <v-icon size="large">
            mdi-cog
          </v-icon>
        </v-btn>
      </template>
      <v-list>
        <v-list-item link @click="enterEdit">
          <v-list-item-title> Bearbeiten </v-list-item-title>
        </v-list-item>
        <v-list-item link @click.stop="toggleDelete">
          <v-list-item-title> Löschen </v-list-item-title>
        </v-list-item>
        <v-list-item link @click="lockProfile">
          <v-list-item-title> Sperren </v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>

    <v-dialog v-model="toDelete" max-width="200">
      <v-card>
        <v-card-text>Willst du dieses Profil wirklich löschen?</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="deleteProfile">
            Ja
          </v-btn>
          <v-btn @click="toggleDelete" class="ml-2">
            Nein
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <div>
    <v-overlay v-model="locked" absolute>
    </v-overlay>

    <DetailComponent :details="detail"/>
    </div>
  </v-container>
</template>

<script lang="ts">
import {useDetailStore} from "@/stores/DetailStore";
import DetailComponent from "@/components/DetailComponent.vue";
import {ref} from "vue";
import router from "@/router";

export default {
  components: {DetailComponent},
  setup() {
    const detailStore = useDetailStore();
    detailStore.loadDemoDetails();
    /*detailStore.loadDetailsById(ID); TODO how are we getting the ID here?*/
    const detail = detailStore.details;

    const locked = ref(false);
    const toDelete = ref(false);

    function enterEdit(): void {
      router.push('/detail/edit/1');
      /*router.push({ name: 'editView', params: { id: detail.getId()}});*/
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
      /*detailStore.deleteDetailsByID(detail.getId()); TODO server/backend problems*/
      router.push(`/`);
    }

    return {
      detail, toDelete, locked,
      enterEdit, toggleDelete,
      lockProfile, deleteProfile
    };
  }
}
</script>

<style scoped>
</style>
