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
      <v-text-field
          v-model="jobTitle"
          :rules="[v => v.length > 1 || 'Erforderlich!']"
          label="Jobtitel"
      ></v-text-field>

      <v-text-field
          v-model="primarySkill"
          :rules="[v => v.length > 1 || 'Erforderlich!']"
          label="PrimärKompetenz"
      ></v-text-field>

      <v-text-field
          v-model="technologies"
          :rules="[v => v.length > 1 || 'Erforderlich!']"
          label="Technologien">
      </v-text-field>

      <v-text-field
          v-model="references"
          :rules="[v => v.length > 1 || 'Erforderlich!']"
          label="Referenzen">
      </v-text-field>
      <v-btn @click="convertDateFormatToISO(birthdate)">Submit Profile</v-btn>
    </v-form>
  </v-container>
</template>
<script>
import {DetailModel} from "@/models/DetailModel";

export default {
  props: {
    details: {
      type: DetailModel,
      default: new DetailModel()
    }
  },
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
    }
  },
  mounted() {
    this.firstName = this.details.getFirstName();
    this.lastName = this.details.getLastName();
    this.degree = this.details.getDegree();
    this.birthdate = this.details.getBirthDate();
    this.jobTitle = this.details.getJobTitle();
    this.primarySkill = this.details.getPrimarySkill();
    this.technologies = this.details.getTechnologies().join(",");
    this.references = this.details.getReferences();
  },
  methods: {
    checkDateFormat(date) {
      return /[0-9]{2}\.[0-9]{2}\.[0-9]{4}/.test(date)
    },
    convertDateFormatToISO(date) {
      return `${date.split(".")[2]}-${date.split(".")[1]}-${date.split(".")[0]}`;
    }
  }
}
</script>

<style scoped>

</style>
