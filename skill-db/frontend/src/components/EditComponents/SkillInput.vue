<template>
  <v-autocomplete v-model="skills"
                  label="Skills"
                  :items="availableSkills"
                  @update:modelValue="emit('update:modelValue', skills)"
                  multiple
                  auto-select-first
                  chips
                  closable-chips/>

  <v-btn v-if="!showAddSkills"
         class="mb-5" size="small" elevation="0"
         @click="showAddSkills = true">
    Skill nicht gefunden?
  </v-btn>
  <v-text-field v-if="showAddSkills"
                v-model="newSkills"
                placeholder="Füge mehrere Skills hinzu, indem du sie mit Kommata [','] separierst."
                @keydown.enter="addSkills()"/>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {loadAvailableSkills} from "@/components/EditComponents/EditAxiosService";

const emit = defineEmits(['update:modelValue'])
const props = defineProps<{
  modelValue: Array<string>,
}>()
//definitions
const showAddSkills = ref(false);
const newSkills = ref('');
const skills = ref(props.modelValue.sort());
let availableSkills = await loadAvailableSkills();

//functions
function addSkills() {
  if (newSkills.value.length > 0) {
    const newSkillsArray = newSkills.value.trim().split(',');
    availableSkills = availableSkills.concat(newSkillsArray);
    skills.value = skills.value.concat(newSkillsArray);
  }
  newSkills.value = '';
  showAddSkills.value = false;
  emit('update:modelValue', skills);
}
</script>
