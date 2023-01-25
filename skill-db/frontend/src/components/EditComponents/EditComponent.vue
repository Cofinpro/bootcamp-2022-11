<template>
  <v-container v-show="!detailStore.loading">
    <v-form @submit.prevent>

      <div class="d-md-flex flex-md-row mt-0 pt-0">
        <div class="pl-3">
          <upload-image-button
              v-on:upload="onUploadProfilePic"
              :old-picture="state.oldPic"
              v-on:toggleDelete="onToggleDelete"/>
        </div>
        <v-row class="pl-md-6 pl-3 pt-12 pt-sm-12 pt-md-2">
          <InputBlock v-model="state"/>
        </v-row>
      </div>

      <v-row class="pt-5">
        <v-col cols="12" lg="6" md="6" sm="12">
          <div class="d-flex flex-column">

            <SkillInput v-model="state.skills"/>

            <v-text-field v-model="state.degree"
                          label="Abschluss"
                          :rules="[v => v.length > 0 || 'Erforderlich']"/>
          </div>
        </v-col>

        <v-col>
          <v-textarea class="references"
                      v-model="state.references"
                      label="Referenzen"
                      :rules="[v => v.length > 0 || 'Erforderlich!']"/>
        </v-col>
      </v-row>

      <div class="buttons d-flex justify-end">

        <ConfirmButton :update="update"
                       :is-valid="isValid"
                       @submit="update ?
                       updateProfile(state, state.profilePicUri,picToDelete)
                       : createProfile(state,state.profilePicUri)"
        />

        <LeaveButton @click="leave"></LeaveButton>
      </div>
    </v-form>
  </v-container>
</template>

<script lang="ts">
import router from "@/router";
import {useDetailStore} from "@/stores/DetailStore";
import {useErrorStore} from "@/stores/ErrorStore";
import UploadImageButton from "@/components/EditComponents/UploadImageButton.vue";
import SkillInput from "@/components/EditComponents/SkillInput.vue";
import ConfirmButton from "@/components/EditComponents/ConfirmButton.vue";
import InputBlock from "@/components/EditComponents/inputBlock.vue";
import LeaveButton from "@/components/EditComponents/LeaveButton.vue";
import {checkDateFormat, checkLength, checkPhoneNumberFormat} from "@/components/EditComponents/ValidationService";
import {EditComponentState} from "@/components/EditComponents/EditComponentState";
import {computed, ref} from "vue";
import {createProfile, updateProfile} from "./EditAxiosService";

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
    let picToDelete = ref(false);
    const state = ref();
    if (props.update) {
      state.value = new EditComponentState
      (
          detailStore.details.getFirstName(),
          detailStore.details.getLastName(),
          detailStore.details.getDegree(),
          detailStore.details.getBirthDate(),
          detailStore.details.getPhoneNumber(),
          detailStore.details.getPrimarySkill(),
          detailStore.details.getJobTitle(),
          detailStore.details.getSkills(),
          detailStore.details.getReferences(),
          detailStore.profilePic
      )
    } else {
      state.value = new EditComponentState
      (
          '', '', '', '', '', '', '', [''], '', ''
      )
    }

    function onToggleDelete(targetValue: boolean) {
      picToDelete.value = targetValue;
      state.value.oldPic = '';
    }


    function onUploadProfilePic(base64String: string) {
      state.value.profilePicUri = base64String;
    }


    function leave() {
      console.log(state);
      if (props.update) {
        const id = detailStore.details.getId();
        router.push({name: 'userDetails', params: {id}});
      } else {
        router.push('/');
      }
    }

    const isValid = computed(() =>{
      return (checkDateFormat(state.value.birthDate) &&
          checkPhoneNumberFormat(state.value.phoneNumber) &&
          checkLength(state.value.references) &&
          checkLength(state.value.firstName) &&
          checkLength(state.value.lastName) &&
          checkLength(state.value.primarySkill) &&
          checkLength(state.value.degree) &&
          checkLength(state.value.jobTitle))
      })

    return {
      detailStore,
      errorStore,
      state,
      picToDelete,
      createProfile,
      updateProfile,
      onToggleDelete,
      onUploadProfilePic,
      leave,
      isValid
    }
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
