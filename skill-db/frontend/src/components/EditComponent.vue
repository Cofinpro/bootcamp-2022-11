<template>
  <v-container>
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
          v-model="jobTitle" :items="jobs" label="Jobprofil"
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
      <v-btn @click="clicked()" elevation="0">Profil erstellen</v-btn>
      <v-btn @click="leave" elevation="0">Abbrechen</v-btn>
    </v-form>
  </v-container>
</template>
<script>
import { ConvertToDetailModelForOutput } from "@/models/DetailModel";
import router from "@/router";
import {useDetailStore} from "@/stores/DetailStore";

export default {
  props: ['detail', 'update'],
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
      jobs: ['Consultant', 'Expert Consultant', 'Senior Consultant', 'Manager', 'Architect', 'Senior Manager', 'Senior Architect', 'Director', 'Partner'],
      primarys: ['Technologie', 'Fach', 'Management'],
      givenTechnologies: ['Java', 'Vue'], /*TODO get from backend*/
    }
  },
  mounted() {
    if(this.update === 'true') {
      this.detail.getFirstName();
      this.firstName = this.detail.getFirstName();
      this.lastName = this.detail.getLastName();
      this.degree = this.detail.getDegree();
      this.birthdate = this.detail.getBirthDate();
      this.jobTitle = this.detail.getJobTitle();
      this.primarySkill = this.detail.getPrimarySkill();
      this.technologies = this.detail.getTechnologies().join(", ");
      this.references = this.detail.getReferences();
    }
  },
  methods: {
    clicked() {
      const detailStore = useDetailStore();
      if(this.update === 'true') {
        const editDetails = ConvertToDetailModelForOutput.toDetail(this);
        /*detailStore.updateProfile(editDetails, this.detail.getId()); TODO server/backend problems*/
        router.push('/test');
        /*router.push( { name: userDetails, params: {this.detail.getId()}});*/
      } else {
        const newDetails = ConvertToDetailModelForOutput.toDetail(this);
        /*detailStore.createProfile(newDetails); TODO server/backend problems*/
        router.push('/');
      }
    },
    leave() {
      if(this.update === 'true') {
        router.push('/test');
        /*router.push( { name: userDetails, params: {this.detail.getId()}});*/
      } else {
        router.push('/');
      }
    },
    checkDateFormat(date) {
      return /[0-3][0-9]\.[0-1][0-9]\.[1-2][0-9]{3}/.test(date)
    },
  },
}
</script>

<style scoped>

</style>
