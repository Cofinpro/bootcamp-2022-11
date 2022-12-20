<template>
  <v-container class="pa-0">
    <v-form @submit.prevent>
      <v-row class="header">
        <v-col cols="3">
          <div class="icon"></div>
        </v-col>
        <v-col class="d-flex flex-column header_content">
          <v-row class="h-100">
            <v-col>
              <v-text-field v-model="firstName" :rules="[v => v.length > 1 || 'Erforderlich!']" label="Vorname"/>
              <v-text-field v-model="lastName" :rules="[v => v.length > 1 || 'Erforderlich']" label="Nachname"/>

              <v-autocomplete v-model="jobTitle" :items="jobs" label="Jobprofil" :rules="[v => v.length > 1 || 'Erforderlich!']"></v-autocomplete>
            </v-col>
            <v-col cols="1">
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
                    <v-btn @click="deleteProfile">
                      Ja
                    </v-btn>
                    <v-btn @click="toggleDelete" class="ml-2">
                      Nein
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>

              <v-overlay v-model="locked" absolute/>
            </v-col>
          </v-row>
          <div class="d-flex w-66 justify-space-between">
            <div class="d-flex flex-column">
              <p>
                <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-email</v-icon>
                max.mustermann@cofinpro.de
              </p>
              <v-autocomplete v-model="primarySkill" :items="primarys" label="Primärkompetenz" :rules="[v => v.length > 1 || 'Erforderlich!']"/>
            </div>
            <div class="d-flex flex-column">
              <p>
                <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-phone</v-icon>
                +49 176 65544 000
              </p>
              <v-text-field v-model="birthdate" :rules="[ date => checkDateFormat(date) || 'Date Format must be DD.MM.YYYY']" label="Geburtsdatum"/>
            </div>
          </div>
        </v-col>
      </v-row>
      <v-row class="pl-6 pr-6">
        <v-col cols="3">
          <div class="pt-2">
            <div class="block_title">Skills</div>
            <div class="block_content">
              <v-autocomplete multiple auto-select-first label="Skills" v-model="technologies" :items="givenTechnologies"
                              :rules="[v => v.length > 1 || 'Erforderlich!']"/>
              <v-text-field v-model="degree" :rules="[v => v.length > 1 || 'Erforderlich']" label="Abschluss"/>
            </div>
          </div>
        </v-col>
        <v-col class="w-100">
          <div class="pl-6 pt-2">
            <div class="block_title">Referenzen</div>
            <div class="block_content">
              <v-text-field v-model="references" :rules="[v => v.length > 1 || 'Erforderlich!']" label="Referenzen"/>
            </div>
          </div>
        </v-col>
      </v-row>
      <v-btn @click="clicked()" elevation="0">Profil erstellen</v-btn>
      <v-btn @click="leave" elevation="0">Abbrechen</v-btn>
    </v-form>
  </v-container>
</template>
<script>
import {ConvertToDetailModelForOutput} from "@/models/DetailModel";
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
      technologies: null,
      references: '',
      jobs: ['Consultant', 'Expert Consultant', 'Senior Consultant', 'Manager', 'Architect', 'Senior Manager', 'Senior Architect', 'Director', 'Partner'],
      primarys: ['Technologie', 'Fach', 'Management'],
      givenTechnologies: ['Java', 'Vue'], /*TODO get from backend*/
    }
  },
  mounted() {
    if (this.update === 'true') {
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
      if (this.update === 'true') {
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
      if (this.update === 'true') {
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
.header {
  display: flex;
  max-width: 100%;
  justify-content: flex-start;
  align-items: flex-start;
  background-color: rgba(255, 255, 255, 1);
}

.header_content {
  height: 256px;
}

.icon {
  display: flex;
  height: 256px;
  border-radius: 12px;
  background-color: rgba(196, 196, 196, 1);
}

.content_card {
  display: flex;
  flex-direction: column;
  max-width: 100%;
  justify-content: flex-start;
  align-items: flex-start;
  border-radius: 8px;
  padding: 8px 12px;
  border-color: rgba(217, 217, 217, 1);
  border-width: 1px;
  border-style: solid;
  background-color: rgba(255, 255, 255, 1);
  width: 100%;
}

.block_title {
  max-width: 100%;
  color: rgba(0, 0, 0, 1);
  font-size: 16px;
  font-weight: bold;
  line-height: 20px;
  letter-spacing: 4px;
  text-align: left;
  font-family: "Poppins", sans-serif;
  width: 100%;
}

.block_content {
  margin-top: 12px;
  font-size: 16px;
  text-align: left;
  font-family: "Poppins", sans-serif;
}

</style>
