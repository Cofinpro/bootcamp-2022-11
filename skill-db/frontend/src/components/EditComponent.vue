<template>
  <v-container>
    <v-form @submit.prevent>

      <div class="d-md-flex flex-md-row mt-0 pt-0">
        <div class="pl-3">
          <upload-image-button
              v-on:upload="onUploadProfilePic"
              :old-picture="oldPic"
              v-on:toggleDelete="onToggleDelete"/>
        </div>
        <v-row class="pl-md-6 pl-3 pt-6 pt-sm-6">
          <InputBlock :date-in="birthdate"
                      :phone-number-in="phoneNumber"
                      :primary-skill-in="primarySkill"
                      :job-title-in="jobTitle"
                      :last-name-in="lastName"
                      :first-name-in="firstName"
                      @update:firstName="(value) => {this.firstName = value}"
                      @update:lastName="(value) => {this.lastName = value}"
                      @update:jobTitle="(value) => {this.jobTitle = value}"
                      @update:primarySkill="(value) => {this.primarySkill = value}"
                      @update:phoneNumber="(value) => {this.phoneNumber = value}"
                      @update:birthDate="(value) => {this.birthdate = value}"
          />
        </v-row>
      </div>

      <v-row class="pt-5">
        <v-col cols="12" lg="6" md="6" sm="12">
          <div class="d-flex flex-column">

            <SkillInput :skills-in="detailStore.details.getSkills()"
                        @update:skills="updateSkills"/>

            <v-text-field v-model="degree"
                          label="Abschluss"
                          :rules="[v => v.length > 0 || 'Erforderlich']"/>
          </div>
        </v-col>

        <v-col>
          <v-textarea class="references"
                      v-model="references"
                      label="Referenzen"
                      :rules="[v => v.length > 0 || 'Erforderlich!']"/>
        </v-col>
      </v-row>

      <div class="buttons d-flex justify-end">

        <ConfirmButton :update="update"
                       :is-valid="isValid"
                       @submit="update ? updateProfile() : createProfile()"
        />

        <LeaveButton @click="leave"></LeaveButton>
      </div>
    </v-form>
  </v-container>
</template>

<script lang="ts">
import {ConvertToDetailModelForOutput} from "@/models/DetailModel";
import router from "@/router";
import {useDetailStore} from "@/stores/DetailStore";
import {useErrorStore} from "@/stores/ErrorStore";
import UploadImageButton from "@/components/EditComponents/UploadImageButton.vue";
import SkillInput from "@/components/EditComponents/SkillInput.vue";
import ConfirmButton from "@/components/EditComponents/ConfirmButton.vue";
import InputBlock from "@/components/EditComponents/inputBlock.vue";
import LeaveButton from "@/components/EditComponents/LeaveButton.vue";

export default {
  name: "EditComponent",
  props: {
    update: {
      type: Boolean,
      required: true
    },
    oldPicture: {
      type: String,
      required: false
    }
  },
  components: {LeaveButton, InputBlock, ConfirmButton, SkillInput, UploadImageButton},
  setup(props) {
    const detailStore = useDetailStore();
    const errorStore = useErrorStore();
    detailStore.loadSkills();
    detailStore.loadPrimarys();
    detailStore.loadJobs();
    let firstName = '';
    let lastName = '';
    let degree = '';
    let birthdate = '';
    let phoneNumber = '';
    let primarySkill = '';
    let jobTitle = '';
    let skills = [] as string[];
    let references = '';
    let newSkills = '';
    let showAddTechnology = false;
    let picToDelete = false;
    let profilePicUri = '';
    let oldPic = '';
    if (props.update) {
      firstName = detailStore.details.getFirstName();
      lastName = detailStore.details.getLastName();
      degree = detailStore.details.getDegree();
      birthdate = detailStore.details.getBirthDate();
      jobTitle = detailStore.details.getJobTitle();
      phoneNumber = detailStore.details.getPhoneNumber();
      primarySkill = detailStore.details.getPrimarySkill();
      skills = detailStore.details.getSkills();
      references = detailStore.details.getReferences();
      oldPic = detailStore.profilePic;
    }
    return {
      detailStore,
      errorStore,
      firstName,
      lastName,
      degree,
      birthdate,
      jobTitle,
      phoneNumber,
      primarySkill,
      skills,
      references,
      newSkills,
      showAddTechnology,
      picToDelete,
      profilePicUri,
      oldPic
    }
  },
  methods: {
    async createProfile() {
      const detailStore = useDetailStore();
      const errorStore = useErrorStore();
      const newDetails = ConvertToDetailModelForOutput.toDetail(this);
      await detailStore.createProfile(newDetails, this.profilePicUri);
      if (!errorStore.hasError) {
        await router.push('/');
      }
    },
    updateSkills(value) {
      console.log(value);
      this.skills=value;
    },
    async updateProfile() {
      const editDetails = ConvertToDetailModelForOutput.toDetail(this);
      const id = this.detailStore.details.getId();
      if (this.picToDelete) {
        await this.deleteProfilePicture();
      }
      await this.detailStore.updateProfile(editDetails, id, this.profilePicUri);
      if ((!this.errorStore.hasError || this.errorStore.allowed)) {
        await router.push({name: 'userDetails', params: {id}});
      }
    },
    async deleteProfilePicture() {
      const detailStore = useDetailStore();
      const id = this.detailStore.details.getId();
      await detailStore.deleteProfilePictureByProfileId(id);
      this.oldPic = '';
    },
    onToggleDelete(targetValue) {
      this.picToDelete = targetValue;
      this.oldPic = '';
    },
    onUploadProfilePic(base64String) {
      this.profilePicUri = base64String;
    },
    leave() {
      if (this.update) {
        const id = this.detailStore.details.getId();
        router.push({name: 'userDetails', params: {id}});
      } else {
        router.push('/');
      }
    },
    addSkills() {
      if (this.newSkills?.length > 0) {
        let skills = this.newSkills.trim().split(',');

        this.givenTechnologies = this.givenTechnologies.concat(skills);
        this.skills = this.skills.concat(skills);
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
    checkPhoneNumberFormat(number) {
      return /^[0-9]{11,13}$/.test(number);
    },
  },
  computed: {
    isValid() {
      return (this.checkDateFormat(this.birthdate) &&
          this.checkPhoneNumberFormat(this.phoneNumber) &&
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

.references {
  min-height: 234px;
}

img {
  height: 200px;
  width: 200px;
}

@media screen and (max-width: 1050px) {
}

@media screen and (max-width: 957px) {

  .buttons {
    flex-direction: column;
  }
}
</style>
