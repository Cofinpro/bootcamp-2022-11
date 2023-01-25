<template>
  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-text-field v-model="state.firstName"
                  @change="this.$emit('update:modelValue',state)"
                  label="Vorname"
                  class="test_firstName"
                  :rules="[v => checkLength(v) || 'Erforderlich!']"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-text-field v-model="state.lastName"
                  @change="this.$emit('update:modelValue',state)"
                  label="Nachname"
                  class="test_lastName"
                  :rules="[v => checkLength(v) || 'Erforderlich']"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-autocomplete v-model="state.jobTitle"
                    @update:modelValue="this.$emit('update:modelValue',state)"
                    label="Jobprofil"
                    class="test_jobTitle"
                    :rules="[v => checkLength(v)|| 'Erforderlich!']"
                    :items="detailStore.jobs"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-autocomplete v-model="state.primarySkill"
                    @update:modelValue="this.$emit('update:modelValue',state)"
                    label="Primärkompetenz"
                    class="test_primarySkill"
                    :rules="[v => checkLength(v)|| 'Erforderlich!']"
                    :items="detailStore.primarys"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-text-field v-model="state.phoneNumber"
                  class="test_phoneNumber"
                  @change="this.$emit('update:modelValue',state)"
                  label="Telefonnummer"
                  :rules="[ number => checkPhoneNumberFormat(number) || 'Min. 11 max. 13 Ziffern']"/>
  </v-col>

  <v-col cols="12" lg="6" md="6" sm="12" class="pa-0 pr-3">
    <v-text-field v-model="state.birthDate"
                  class="test_birthdate"
                  @change="this.$emit('update:modelValue',state)"
                  label="Geburtsdatum"
                  :rules="[ date => checkDateFormat(date) ||
                          'Datum muss im Format TT.MM.JJJJ eingegeben werden!']"/>
  </v-col>

</template>

<script setup lang="ts">
import {useDetailStore} from "@/stores/DetailStore";
import {InputBlockState} from "@/components/EditComponents/inputBlockState";
import {ref} from "vue";
import {checkDateFormat, checkLength, checkPhoneNumberFormat} from "@/components/EditComponents/ValidationService";

const emit = defineEmits(['update:modelValue'])
const props = defineProps<{
  modelValue: InputBlockState
}>();
const state = ref(props.modelValue);
const detailStore = useDetailStore();
</script>

<style scoped>

</style>
