<template>
  <v-container>
    <h1>{{ details.getFirstName() }}'s Profil</h1>
    <h2>Hier kannst das Profil anschauen und bearbeiten!</h2>
    <h4>Name</h4>
    <v-chip>{{ details.getFirstName() }} {{details.getLastName()}}</v-chip>
    <h4>Alter</h4>
    <v-chip>{{ details.getAge() }}</v-chip>
    <h4>Abschluss</h4>
    <v-chip>{{ details.getDegree() }}</v-chip>
    <h4>Jobtitel</h4>
    <v-chip>{{ details.getJobTitle() }}</v-chip>
    <h4>Primärkompetenz</h4>
    <v-chip>{{ details.getPrimarySkill() }}</v-chip>
    <h4>Technologien</h4>
    <ul>
      <li v-for="technology in details.getTechnologies()">
        <v-chip>{{ technology }}</v-chip>
      </li>
    </ul>
    <h4>Referenzen</h4>
    <ul>
      <li v-for="reference in details.getReferences().split('\n')">
        <v-chip>{{ reference }}</v-chip>
      </li>
    </ul>
  </v-container>
</template>

<script>
export default {
  props: ['details'],
}
</script>

<style scoped>

</style>
