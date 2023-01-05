<template>
  <v-container>
    <v-form @submit.prevent>

      <div class="header">
        <div class="d-flex flex-column align-items-center">
          <upload-image-button/>
        </div>

        <v-row class="headline">
          <v-col cols="12" lg="6" md="6" sm="12">
            <v-text-field v-model="firstName" label="Vorname"
                          :rules="[v => v.length > 1 || 'Erforderlich!']"/>
            <v-autocomplete v-model="jobTitle" label="Jobprofil"
                            :rules="[v => v.length > 1 || 'Erforderlich!']"
                            :items="jobs"/>
            <v-text-field v-model="phoneNumber" label="Telefonnummer"
                          :rules="[ number => checkPhoneFormat(number) || 'Min. 11 max. 13 Ziffern']"/>
          </v-col>

          <v-col lg="6" md="6" sm="12">
            <v-text-field v-model="lastName" label="Nachname"
                          :rules="[v => v.length > 1 || 'Erforderlich']"/>
            <v-autocomplete v-model="primarySkill" label="Primärkompetenz"
                            :rules="[v => v.length > 1 || 'Erforderlich!']"
                            :items="primaries"/>
            <v-text-field v-model="birthdate" label="Geburtsdatum"
                          :rules="[ date => checkDateFormat(date) || 'Date Format must be DD.MM.YYYY']"/>
          </v-col>
        </v-row>
      </div>

      <v-row>
        <v-col cols="12" lg="6" md="6" sm="12">
          <div class="skillsAndDegree d-flex flex-column">
            <v-autocomplete v-model="technologies" label="Skills"
                            :items="givenTechnologies"
                            multiple auto-select-first
                            chips closable-chips/>

            <v-btn class="mb-5" size="small" elevation="0"
                   v-if="!showAddTechnology" @click="showAddTechnology=true">
              Technologie nicht gefunden?
            </v-btn>
            <v-text-field v-if="showAddTechnology" v-model="newTechnologies"
                          placeholder="Füge mehrere Skills hinzu, indem du sie mit Kommata [','] separierst."
                          @keydown.enter="addSkills"/>

            <v-text-field v-model="degree" label="Abschluss"
                          :rules="[v => v.length > 1 || 'Erforderlich']"/>
          </div>
        </v-col>

        <v-col>
          <v-text-field class="references" v-model="references" label="Referenzen"
                        :rules="[v => v.length > 1 || 'Erforderlich!']"/>
        </v-col>
      </v-row>

      <div class="buttons d-flex justify-end">
        <v-btn v-if="update === 'false'" class="mt-10"
               :style="!isFilled ? {
                  color: '#BDBDBD !important',
                  border: '1px dashed #BBBBBB !important',
                } : ''"
               :disabled="!isFilled"
               @click="submitProfile()" elevation="0">
          Profil erstellen
        </v-btn>
        <v-btn v-if="update === 'true'" class="mt-10"
               @click="submitProfile()" elevation="0">
          Änderungen speichern
        </v-btn>
        <v-btn class="mt-10 ml-lg-5 ml-md-5"
               @click="leave" elevation="0">
          Abbrechen
        </v-btn>
      </div>
    </v-form>
  </v-container>
</template>

<script> /*TODO should be TypeScript*/
import {ConvertToDetailModelForOutput} from "@/models/DetailModel";
import router from "@/router";
import {useDetailStore} from "@/stores/DetailStore";
import {useRoute} from "vue-router";
import {useErrorStore} from "@/stores/ErrorStore";
import UploadImageButton from "@/components/UploadImageButton.vue";

export default {
  name: "EditComponent",
  props: ['detail', 'update'],
  components: { UploadImageButton },
  setup() {
    const detailStore = useDetailStore();
    const errorStore = useErrorStore();
    const id = String(useRoute().params.id);

    return {
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
      primaries: [],
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
    this.primaries = detailStore.primarys;

    if (this.update === 'true') {
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
    async submitProfile() {
      const detailStore = useDetailStore();
      const errorStore = useErrorStore();
      console.log(this.isFilled);
      if (this.update === 'true') {
        const editDetails = ConvertToDetailModelForOutput.toDetail(this);
        const id = this.detail.getId();
        await detailStore.updateProfile(editDetails, id);
        if (!errorStore.hasError) {
          await router.push({name: 'userDetails', params: {id}});
        }
      } else {
        const newDetails = ConvertToDetailModelForOutput.toDetail(this);
        await detailStore.createProfile(newDetails);
        if (!errorStore.hasError) {
          await router.push('/');
        }
      }
    },
    leave() {
      if (this.update === 'true') {
        const id = this.detail.getId();
        router.push({name: 'userDetails', params: {id}});
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
      const regex = /^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$/;
      if (!regex.test(date)) return false;

      const currentDate = new Date();
      const [day, month, year] = date.split('.');
      const inputDate = new Date(Date.UTC(year, month - 1, day));

      return inputDate <= currentDate;
    },
    checkPhoneNumber(number) {
      const regex = /[0-9]*\/*(\+49)* *(\([0-9]+\))*( *([-–])* *[0-9]+)*/g;
      return regex.test(number);
    },
  },
  computed: {
    isFilled() {
      return (this.checkDateFormat(this.birthdate) &&
          this.checkPhoneNumber(this.phoneNumber) &&
          this.references.length > 0 &&
          this.jobTitle.length > 0 &&
          this.primarySkill.length > 0 &&
          this.lastName.length > 0 &&
          this.firstName.length > 0 &&
          this.degree.length > 0)
    },
  }
}
</script>

<style scoped>

.header {
  width: 100%;
  display: flex;
}

.uploadBtn {
  width: 200px;
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
