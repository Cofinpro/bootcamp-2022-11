<template>
  <v-autocomplete v-model="skills"
                  label="Skills"
                  :items="detailStore.skills"
                  @update:modelValue="emitAndLog"
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

<script>
import {useDetailStore} from "@/stores/DetailStore";

export default {
  name: "SkillInput",
  emits: ['update:skills'],
  props: {
    skillsIn:{
      value: [],
      required: true,
    }
  },
  data(props) {
    let skills = props.skillsIn;
    let newSkills = '';
    let showAddSkills = false;
    return {
      detailStore: useDetailStore(),
      showAddSkills,
      newSkills,
      skills
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
    emitAndLog() {
      console.log(this.skills)
      this.$emit('update:skills',this.skills)
    },
    toggleShowAddSkills() {
      this.showAddSkills=true
    },
  }
}
</script>

<style scoped>

</style>
