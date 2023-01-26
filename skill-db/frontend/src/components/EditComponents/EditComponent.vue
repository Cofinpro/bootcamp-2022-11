<template>
  <v-container v-show="!detailStore.loading">
    <v-form @submit.prevent>

      <div class="d-md-flex flex-md-row mt-0 pt-0">
        <div class="pl-3">
          <upload-image-button
              @upload="onUploadProfilePic"
              :old-picture="state.oldPic"
              @toggleDelete="onToggleDelete"/>
        </div>
        <v-row class="pl-md-6 pl-3 pt-12 pt-sm-12 pt-md-2">
          <Suspense>
            <InputBlock v-model="state"/>
          </Suspense>
        </v-row>
      </div>

      <v-row class="pt-5">
        <v-col cols="12" lg="6" md="6" sm="12">
          <div class="d-flex flex-column">
            <Suspense>
              <SkillInput v-model="state.skills"/>
            </Suspense>
            <v-text-field v-model="state.degree"
                          label="Abschluss"
                          :rules="[v => checkLength(v) || 'Erforderlich']"/>
          </div>
        </v-col>

        <v-col>
          <v-textarea class="references"
                      v-model="state.references"
                      label="Referenzen"
                      :rules="[v => checkLength(v) || 'Erforderlich!']"/>
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

<script setup lang="ts">
import router from "@/router";
import {useDetailStore} from "@/stores/DetailStore";
import {useErrorStore} from "@/stores/ErrorStore";
import UploadImageButton from "@/components/EditComponents/UploadImageButton.vue";
import SkillInput from "@/components/EditComponents/SkillInput.vue";
import ConfirmButton from "@/components/EditComponents/ConfirmButton.vue";
import InputBlock from "@/components/EditComponents/inputBlock.vue";
import LeaveButton from "@/components/EditComponents/LeaveButton.vue";
import {
  checkLength,
  checkState
} from "@/components/EditComponents/ValidationService";
import {
  emptyEditComponentState,
  storeToEditComponentState
} from "@/components/EditComponents/EditComponentState";
import {computed, ref} from "vue";
import {createProfile, updateProfile} from "./EditAxiosService";

const props = defineProps({
  update: Boolean,
});

const detailStore = useDetailStore();
const errorStore = useErrorStore();
let picToDelete = ref(false);
const state = ref();
if (props.update) {
  state.value = storeToEditComponentState();
} else {
  state.value = emptyEditComponentState();
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

const isValid = computed(() => {
  return checkState(state.value)
})
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
