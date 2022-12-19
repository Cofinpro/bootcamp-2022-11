<template>
  <v-container>
    <h1>{{ details.getFirstName() }}'s Profil</h1>
    <h2>Hier kannst das Profil anschauen und bearbeiten!</h2>
    <v-form @submit.prevent>
      <v-text-field v-model="firstName"
                    :rules="[v => v.length > 1 || 'Erforderlich!']"
                    label="Vorname"
      ></v-text-field>

      <v-text-field v-model="lastName"
                    :rules="[v => v.length > 1 || 'Erforderlich']"
                    label="Nachname"
      ></v-text-field>
      <v-text-field v-model="degree"
                    :rules="[v => v.length > 1 || 'Erforderlich']"
                    label="Abschluss"
      ></v-text-field>

      <v-text-field
          v-model="birthdate"
          :rules="[ date => checkDateFormat(date) || 'Date Format must be DD.MM.YYYY']"
          label="Geburtsdatum"></v-text-field>

      <v-autocomplete
          v-model="jobTitle" :items="jobs" label="Jobbezeichnung"
          :rules="[v => v.length > 1 || 'Erforderlich!']">
      </v-autocomplete>

      <v-autocomplete
          v-model="primarySkill" :items="primarys" label="Primärkompetenz"
          :rules="[v => v.length > 1 || 'Erforderlich!']">
      </v-autocomplete>

      <v-autocomplete
          multiple auto-select-first label="Technologien"
          v-model="technologies" :items="givenTechnologies"
          :rules="[v => v.length > 1 || 'Erforderlich!']">
      </v-autocomplete>

      <v-text-field
          v-model="references"
          :rules="[v => v.length > 1 || 'Erforderlich!']"
          label="Referenzen">
      </v-text-field>
      <v-btn @click="convertDateFormatToISO(birthdate)" elevation="0">Profil erstellen</v-btn>
    </v-form>
  </v-container>
</template>
<script>
import {DetailModel} from "@/models/DetailModel";

export default {
  props: ["details"]/*{
    details: {
      type: DetailModel,
      default: new DetailModel()
    }
  }*/,
  data() {
    return {
      firstName: '',
      lastName: '',
      degree: '',
      birthdate: '',
      jobTitle: '',
      primarySkill: '',
      technologies: '',
      references: '',
      jobs: ['Consultant', 'Expert Consultant', 'Senior Consultant', 'Manager', 'Architect'],
      primarys: ['Tech', 'Fach', 'Managment'],
      givenTechnologies: ['Java', 'Vue'],
    }
  },
  mounted() {
    this.firstName = this.details.getFirstName();
    this.lastName = this.details.getLastName();
    this.degree = this.details.getDegree();
    this.birthdate = this.details.getBirthDate();
    this.jobTitle = this.details.getJobTitle();
    this.primarySkill = this.details.getPrimarySkill();
    this.technologies = this.details.getTechnologies().join(", ");
    this.references = this.details.getReferences();
  },
  methods: {
    checkDateFormat(date) {
      return /[0-3][0-9]\.[0-1][0-9]\.[1-2][0-9]{3}/.test(date)
    },
    convertDateFormatToISO(date) {
      return `${date.split(".")[2]}-${date.split(".")[1]}-${date.split(".")[0]}`;
    }
  }
}
</script>

<style scoped>

</style>
