<template>
  <v-autocomplete v-model="skills"
                  label="Skills"
                  :items="detailStore.skills"
                  @update:modelValue="onInput"
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

<script lang="ts">
import {useDetailStore} from "@/stores/DetailStore";
import {ref, defineEmits} from "vue";

export default {
  name: "SkillInput",
  props: {
    skillsIn:{
      value: [] as string[],
      required: true,
    }
  },
  setup(props, context) {
    //definitions
    const showAddSkills = ref(false);
    const newSkills = ref('');
    const skills = ref(props.skillsIn.sort());
    const detailStore = useDetailStore();
    defineEmits(['update:skills']);

    //functions
    function addSkills() {
      if (newSkills.value.length > 0) {
        const newSkillsArray = newSkills.value.trim().split(',');
        detailStore.skills = detailStore.skills.concat(newSkillsArray);
        skills.value = skills.value.concat(newSkillsArray);
      }
      newSkills.value = '';
      showAddSkills.value = false;
      onInput();
    }

    function onInput() {
      context.emit('update:skills',skills)
    }
    return {
      detailStore,
      showAddSkills,
      newSkills,
      skills,
      addSkills,
      onInput
    }
  },
}
</script>
