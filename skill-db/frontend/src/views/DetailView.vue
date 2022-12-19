<template>
  <v-container>
    <h1>{{ detail.getFirstName() }}'s Profil</h1>
    <h2>Hier kannst du das Profil anschauen und bearbeiten!</h2>

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
        <v-list-item link @click="toggleEdit">
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

    <DetailComponent v-if="!editMode" :details="detail"/>
    <EditComponent v-if="editMode" :details="detail" v-on:editMode="editMode"/>
  </v-container>
</template>

<script lang="ts">
import {useDetailStore} from "@/stores/DetailStore";
import DetailComponent from "@/components/DetailComponent.vue";
import EditComponent from "@/components/EditComponent.vue";
import {ref} from "vue";
import router from "@/router";

export default {
  components: {EditComponent, DetailComponent},
  setup() {
    const detailStore = useDetailStore();
    detailStore.loadDemoDetails();
    const detail = detailStore.details;

    const references = detail.getReferences().split(",");

    let showSettings: boolean = false;
    const editMode = ref(false);
    const locked = ref(false);
    const toDelete = ref(false);

    function toggleEdit(): void {
      editMode.value = true;
    }

    function toggleDelete(): void {
      toDelete.value = !toDelete.value;
    }

    function lockProfile(): void {
      locked.value = !locked.value;
      console.log("This profile is now locked away.");
    }

    function deleteProfile(): void {
      /*ask a second time, before delete*/
      console.log("You want to delete this profile? OK.")
      router.push(`/`);
    }

    return {
      detail, showSettings, editMode, toDelete,
      toggleEdit, toggleDelete,
      lockProfile, deleteProfile,
      references
    };
  }
}
</script>

<style scoped>
.v-chip {
  margin-bottom: 10px;
}
</style>
