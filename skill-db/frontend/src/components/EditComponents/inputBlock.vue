<template>
  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-text-field v-model="state.firstName"
                  @change="this.$emit('update:firstName',state.firstName)"
                  label="Vorname"
                  class="test_firstName"
                  :rules="[v => checkLength(v) || 'Erforderlich!']"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-text-field v-model="state.lastName"
                  @change="this.$emit('update:lastName',state.lastName)"
                  label="Nachname"
                  class="test_lastName"
                  :rules="[v => checkLength(v) || 'Erforderlich']"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-autocomplete v-model="state.jobTitle"
                    @update:modelValue="this.$emit('update:jobTitle',state.jobTitle)"
                    label="Jobprofil"
                    class="test_jobTitle"
                    :rules="[v => checkLength(v)|| 'Erforderlich!']"
                    :items="detailStore.jobs"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-autocomplete v-model="state.primarySkill"
                    @update:modelValue="this.$emit('update:primarySkill',state.primarySkill)"
                    label="Primärkompetenz"
                    class="test_primarySkill"
                    :rules="[v => checkLength(v)|| 'Erforderlich!']"
                    :items="detailStore.primarys"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-text-field v-model="state.phoneNumber"
                  class="test_phoneNumber"
                  @change="this.$emit('update:phoneNumber',state.phoneNumber)"
                  label="Telefonnummer"
                  :rules="[ number => checkPhoneNumberFormat(number) || 'Min. 11 max. 13 Ziffern']"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-text-field v-model="state.birthdate"
                  class="test_birthdate"
                  @change="this.$emit('update:birthDate',state.birthdate)"
                  label="Geburtsdatum"
                  :rules="[ date => checkDateFormat(date) ||
                          'Datum muss im Format TT.MM.JJJJ eingegeben werden!']"/>
  </v-col>

</template>

<script lang="ts">
import {useDetailStore} from "@/stores/DetailStore";
import {inputBlockState} from "@/components/EditComponents/inputBlockState";
import {ref} from "vue";
import {checkDateFormat, checkLength, checkPhoneNumberFormat} from "@/components/EditComponents/ValidationService";

export default {
  emits: [
    'update:firstName',
    'update:lastName',
    'update:jobTitle',
    'update:primarySkill',
    'update:phoneNumber',
    'update:birthDate'
  ],
  props: {
    firstNameIn: {
      required: true,
      type: String
    },
    lastNameIn: {
      required: true,
      type: String
    },
    jobTitleIn: {
      required: true,
      type: String
    },
    primarySkillIn: {
      required: true,
      type: String
    },
    phoneNumberIn: {
      required: true,
      type: String
    },
    dateIn: {
      required: true,
      type: String
    },
  },
  setup(props) {
    const state = ref(new inputBlockState(
        props.firstNameIn, props.lastNameIn, props.jobTitleIn, props.primarySkillIn, props.dateIn, props.phoneNumberIn
    ));
    return {
      state,
      checkDateFormat,
      checkLength,
      checkPhoneNumberFormat,
      detailStore: useDetailStore(),
    };
  },
}
</script>

<style scoped>

</style>
