<template>
  <div class="d-flex justify-end">
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
        <v-list-item v-if="!edit" link @click="enterEdit(id)">
          <v-list-item-title> Bearbeiten</v-list-item-title>
        </v-list-item>
        <v-list-item link @click.stop="toggleDelete">
          <v-list-item-title> Löschen</v-list-item-title>
        </v-list-item>
        <v-list-item link @click="lockProfile">
          <v-list-item-title> Sperren</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>

    <v-dialog v-model="toDelete" max-width="200">
      <v-card>
        <v-card-text>Willst du dieses Profil wirklich löschen?</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="deleteProfile(id)">
            Ja
          </v-btn>
          <v-btn @click="toggleDelete" class="ml-2">
            Nein
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-overlay v-model="locked" absolute/>
  </div>
</template>

<script lang="ts">
import router from "../router";
import {ref} from "vue";
import {useDetailStore} from "@/stores/DetailStore";

export default {
  name: "SettingsButton",
  props: {
    id: Number,
    edit: Boolean,
  },
  setup() {
    const locked = ref(false);
    const toDelete = ref(false);

    function enterEdit(id: number): void {
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

    function deleteProfile(id: number): void {
      const detailStore = useDetailStore();
      detailStore.deleteDetailsByID(id);
      router.push(`/`);
    }

    return {
      locked, toDelete,
      enterEdit, toggleDelete, lockProfile, deleteProfile
    }
  }
}
</script>

<style scoped>

</style>