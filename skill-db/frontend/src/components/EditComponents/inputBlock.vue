<template>
  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-text-field v-model="firstName"
                  @change="this.$emit('update:firstName',this.firstName)"
                  label="Vorname"
                  class="test_firstName"
                  :rules="[v => v.length > 0 || 'Erforderlich!']"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-text-field v-model="lastName"
                  @change="this.$emit('update:lastName',this.lastName)"
                  label="Nachname"
                  class="test_lastName"
                  :rules="[v => v.length > 0 || 'Erforderlich']"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-autocomplete v-model="jobTitle"
                    @update:modelValue="this.$emit('update:jobTitle',this.jobTitle)"
                    label="Jobprofil"
                    class="test_jobTitle"
                    :rules="[v => v.length > 0 || 'Erforderlich!']"
                    :items="detailStore.jobs"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-autocomplete v-model="primarySkill"
                    @update:modelValue="this.$emit('update:primarySkill',this.primarySkill)"
                    label="Primärkompetenz"
                    class="test_primarySkill"
                    :rules="[v => v.length > 0 || 'Erforderlich!']"
                    :items="detailStore.primarys"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-text-field v-model="phoneNumber"
                  class="test_phoneNumber"
                  @change="this.$emit('update:phoneNumber',this.phoneNumber)"
                  label="Telefonnummer"
                  :rules="[ number => checkPhoneNumberFormat(number) || 'Min. 11 max. 13 Ziffern']"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-text-field v-model="birthdate"
                  class="test_birthdate"
                  @change="this.$emit('update:birthDate',this.birthdate)"
                  label="Geburtsdatum"
                  :rules="[ date => checkDateFormat(date) ||
                          'Datum muss im Format TT.MM.JJJJ eingegeben werden!']"/>
  </v-col>

</template>

<script lang="ts">
import {useDetailStore} from "@/stores/DetailStore";

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
  data(props) {
    return {
      firstName: props.firstNameIn,
      lastName: props.lastNameIn,
      jobTitle: props.jobTitleIn,
      primarySkill: props.primarySkillIn,
      phoneNumber: props.phoneNumberIn,
      birthdate: props.dateIn,
      detailStore: useDetailStore(),
    };
  },
  methods: {
    checkDateFormat(date: string): Boolean {
      const regex = /^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$/;
      if (!regex.test(date)) return false;

      const currentDate = new Date();
      const [day, month, year] = date.split('.');
      const inputDate = new Date(Date.UTC(year, month, day));

      return inputDate <= currentDate;
    },
    checkPhoneNumberFormat(number: string): Boolean {
      return /^[0-9]{11,13}$/.test(number);
    },
  },
}
</script>

<style scoped>

</style>
