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
  </div>

  <v-container>
    <v-form @submit.prevent>
      <div class="header">
        <div class="d-flex flex-column align-items-center">
          <img src="@/assets/images/dummy_profilePicture.png" alt="Profilbild">
          <v-card class="mt-3 mb-10 d-flex justify-center" color="grey" width="200px" height="25px">Bild hochladen</v-card>
        </div>
        <v-row class="headline">
          <v-col cols="12" lg="6" md="6" sm="12">
            <v-text-field v-model="firstName" :rules="[v => v.length > 1 || 'Erforderlich!']" label="Vorname"/>
            <v-autocomplete v-model="jobTitle" :items="jobs" label="Jobprofil" :rules="[v => v.length > 1 || 'Erforderlich!']"></v-autocomplete>
            <v-text-field v-model="phoneNumber" label="Telefonnummer" :rules="[ number => checkPhoneFormat(number) || 'Min. 11 max. 13 Ziffern']"/>
          </v-col>
          <v-col lg="6" md="6" sm="12">
            <v-text-field v-model="lastName" :rules="[v => v.length > 1 || 'Erforderlich']" label="Nachname"/>
            <v-autocomplete v-model="primarySkill" :items="primarys" label="Primärkompetenz" :rules="[v => v.length > 1 || 'Erforderlich!']"/>
            <v-text-field v-model="birthdate" label="Geburtsdatum" :rules="[ date => checkDateFormat(date) || 'Date Format must be DD.MM.YYYY']"/>
          </v-col>
        </v-row>
      </div>

      <v-row>
        <v-col cols="12" lg="6" md="6" sm="12">
          <div class="skillsAndDegree d-flex flex-column">
            <v-autocomplete
                multiple
                auto-select-first
                label="Skills"
                chips
                closable-chips
                v-model="technologies"
                :items="givenTechnologies"
            />

            <v-btn class="mb-5" size="small" v-if="!showAddTechnology" @click="showAddTechnology=true"
                   elevation="0">Technologie nicht gefunden?
            </v-btn>
            <v-text-field v-if="showAddTechnology" v-model="newTechnologies"
                          placeholder="Füge mehrere Skills hinzu, indem du sie mit Kommata [','] separierst."
                          @keydown.enter="addSkills"
            />

            <v-text-field v-model="degree" label="Abschluss" :rules="[v => v.length > 1 || 'Erforderlich']"/>
          </div>
        </v-col>
        <v-col>
          <v-text-field class="references" v-model="references" label="Referenzen"
                        :rules="[v => v.length > 1 || 'Erforderlich!']"/>
        </v-col>
      </v-row>

      <div class="buttons d-flex justify-end">
        <v-btn class="mt-10" @click="submitProfile()" elevation="0">Profil erstellen</v-btn>
        <v-btn class="mt-10 ml-lg-5 ml-md-5" @click="leave" elevation="0">Abbrechen</v-btn>
      </div>
    </v-form>
  </v-container>
</template>
<script>
import {ConvertToDetailModelForOutput} from "@/models/DetailModel";
import router from "@/router";
import {useDetailStore} from "@/stores/DetailStore";
import {ref} from "vue";
import {useRoute} from "vue-router";

export default {
  props: ['detail', 'update'],
  setup() {
    const detailStore = useDetailStore();
    const id = Number(useRoute().params.id);
    detailStore.loadDetailsById(id);

    const locked = ref(false);
    const toDelete = ref(false);

    function enterEdit() {
      router.push({ name: 'editView', params: { id: this.id}});
    }

    function toggleDelete() {
      toDelete.value = !toDelete.value;
    }

    function lockProfile() {
      locked.value = true;
      console.log("This profile is now locked away.");
      /*router.push(`/`);*/
    }

    function deleteProfile() {
      detailStore.deleteDetailsByID(this.id);
      router.push(`/`);
    }

    return {
      toDelete, locked,
      enterEdit, toggleDelete,
      lockProfile, deleteProfile
    };
  },
  data() {
    return {
      firstName: '',
      lastName: '',
      degree: '',
      birthdate: '',
      phoneNumber: '',
      jobTitle: '',
      primarySkill: '',
      technologies: [],
      references: '',
      jobs: [],
      primarys: [],
      givenTechnologies: [],
      newTechnologies: '',
      showAddTechnology: false,
    }
  },
  created() {
    const detailStore = useDetailStore();
    detailStore.getSkills();
    detailStore.getJobs();
    detailStore.getPrimarys();
    this.givenTechnologies = detailStore.skills;
    this.jobs = detailStore.jobs;
    this.primarys = detailStore.primarys;

    if (this.update === 'true') {
      this.detail.getFirstName();
      this.firstName = this.detail.getFirstName();
      this.lastName = this.detail.getLastName();
      this.degree = this.detail.getDegree();
      this.birthdate = this.detail.getBirthDate();
      this.phoneNumber = this.detail.getPhoneNumber();
      this.jobTitle = this.detail.getJobTitle();
      this.primarySkill = this.detail.getPrimarySkill();
      this.technologies = this.detail.getTechnologies();
      this.references = this.detail.getReferences();
    }
  },
  methods: {
    submitProfile() {
      const detailStore = useDetailStore();
      if (this.update === 'true') {
        const editDetails = ConvertToDetailModelForOutput.toDetail(this);
        const id = this.detail.getId();
        detailStore.updateProfile(editDetails, id);
        router.push( { name: 'userDetails', params: {id}});
      } else {
        const newDetails = ConvertToDetailModelForOutput.toDetail(this);
        detailStore.createProfile(newDetails);
        router.push('/');
      }
    },
    leave() {
      if (this.update === 'true') {
        const id = this.detail.getId();
        router.push( { name: 'userDetails', params: {id}});
      } else {
        router.push('/');
      }
    },
    addSkills() {
      if (this.newTechnologies?.length > 0) {
        let skills = this.newTechnologies.trim().split(',');

        this.givenTechnologies = this.givenTechnologies.concat(skills);
        this.technologies = this.technologies.concat(skills);
      }

      this.newTechnologies = '';
      this.showAddTechnology = false;
    },
    checkDateFormat(date) {
      return /[0-3][0-9]\.[0-1][0-9]\.[1-2][0-9]{3}/.test(date)
    },
    checkPhoneFormat(number) {
      return /^[0-9]{11,13}$/.test(number);
    }
  },
}
</script>

<style scoped>

.header {
  width: 100%;
  display: flex;
}
.headline {
  margin-left: 20px;
}
img {
  height: 200px;
  width: 200px;
}
.skillsAndDegree {
  margin-left: -10px;
}

@media screen and (max-width: 1050px) {
  .header {
    display: grid;
    grid-template-rows: 1fr 1fr;
  }
  img {
    grid-row: 1;
    margin-left: 0;
  }
  .headline {
    margin-left: -10px;
  }
  .skillsAndDegree {
    margin-left: 0;
  }
}
@media screen and (max-width: 957px) {
  .header {
    display: grid;
    grid-template-rows: 0.5fr 1fr;
  }
  img {
    grid-row: 1;
    margin-left: 0;
  }
  .headline {
    grid-row: 2;
    margin-left: -10px;
    margin-bottom: 10px;
  }
  .skillsAndDegree {
    margin-left: 0;
  }
  .buttons {
    flex-direction: column;

  }
}

</style>
