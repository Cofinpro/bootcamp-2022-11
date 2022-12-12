<template>
  <v-container>
    <h1>{{ detail.getName() }}'s Profil</h1>
    <h2>Hier kannst das Profil anschauen und bearbeiten!</h2>
    <v-btn outlined color="red">
      Edit
      <v-icon color="white">mdi-email</v-icon>
    </v-btn>
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

<script>
import {useDetailStore} from "@/stores/DetailStore";
import {DetailModel} from "@/models/DetailModel";

export default {
  setup() {
    const detailStore = useDetailStore();
    detailStore.loadDemoDetails();
    const detail = detailStore.details;
    return {
      detail,
    };
  }
}
</script>

<style scoped>
.v-chip {
  margin-bottom: 10px;
}
</style>
