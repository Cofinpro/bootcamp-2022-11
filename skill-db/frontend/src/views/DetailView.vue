<template>
  <v-container>
    <h1>{{ detail.getName() }}'s Profil</h1>
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
        <v-list-item link>
          <v-list-item-title @click="toggleEdit"> Bearbeiten </v-list-item-title>
        </v-list-item>
        <v-list-item link>
          <v-list-item-title @click="deleteProfile"> Löschen </v-list-item-title>
        </v-list-item>
        <v-list-item link>
          <v-list-item-title @click="lockProfile"> Sperren </v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>


    <h4>Name</h4>
    <v-chip>{{ detail.getName() }}</v-chip>
    <h4>Alter</h4>
    <v-chip>{{ detail.getAge() }}</v-chip>
    <h4>Jobtitel</h4>
    <v-chip>{{ detail.getJobTitle() }}</v-chip>
    <h4>Primärkompetenz</h4>
    <v-chip>{{ detail.getPrimarySkill() }}</v-chip>
    <h4>Technologien</h4>
    <ul>
      <li v-for="technology in detail.getTechnologies()">
        <v-chip>{{ technology }}</v-chip>
      </li>
    </ul>
    <h4>Referenzen</h4>
    <ul>
      <li v-for="reference in detail.getReferences()">
        <v-chip>{{ reference }}</v-chip>
      </li>
    </ul>
  </v-container>
</template>

<script lang="ts">
import {useDetailStore} from "@/stores/DetailStore";

export default {
  setup() {
    const detailStore = useDetailStore();
    detailStore.loadDemoDetails();
    const detail = detailStore.details;

    let showSettings: boolean = false;
    let editMode: boolean = false;
    let locked: boolean = false;

    return {
      detail, showSettings,
      editMode, locked
    };
  },
  methods: {
    toggleEdit(): void {
      this.editMode = !this.editMode;
      console.log("Now you can edit.");
    },
    lockProfile(): void {
      this.locked = !this.locked;
      console.log("This profile is now locked away.");
    },
    deleteProfile(): void {
      /*ask a second time, before delete*/
      console.log("You want to delete this profile? OK.")
    },
  }
}
</script>

<style scoped>
.v-chip {
  margin-bottom: 10px;
}
</style>
