<template>
  <v-autocomplete :v-model="skills"
                  label="Skills"
                  :items="detailStore.skills"
                  @update:modelValue="onInput"
                  multiple
                  auto-select-first
                  chips
                  closable-chips/>

  <v-btn v-if="!showAddSkills"
         class="mb-5" size="small" elevation="0"
         @click="toggleShowAddSkills">
    Skill nicht gefunden?
  </v-btn>
  <v-text-field v-if="showAddSkills"
                v-model="newSkills"
                placeholder="Füge mehrere Skills hinzu, indem du sie mit Kommata [','] separierst."
                @keydown.enter="addSkills"/>
</template>

<script lang="ts">
import {useDetailStore} from "@/stores/DetailStore";

export default {
  name: "SkillInput",
  props: {
    skillsIn:{
      value: [] as string[],
      required: true,
    }
  },
  data(props) {
    return {
      detailStore: useDetailStore(),
      showAddSkills: false,
      newSkills: '',
      skills: props.skillsIn
    }
  },
  methods: {
    addSkills() {
      if (this.newSkills?.length > 0) {
        let newSkillsArray = this.newSkills.trim().split(',');

        this.detailStore.skills = this.detailStore.skills.concat(newSkillsArray);
        this.skills = this.skills.concat(newSkillsArray);
      }

      this.newSkills = '';
      this.showAddSkills = false;
    },
    onInput() {
      this.$emit('updateSkills',this.skills)
    },
    toggleShowAddSkills() {
      this.showAddSkills=true
    },
  }
}
</script>

<style scoped>

</style>
