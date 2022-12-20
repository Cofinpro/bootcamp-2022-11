<template>
  <div>
    <div class="d-flex flex-column dropdown">
      <div class="d-flex w-100">
        <input v-model="searchTerm" @focus="showOptions=true" placeholder="Search options"/>
        <button v-on:click="toggleDropdown">Toggle</button>
      </div>
      <div class="d-flex flex-wrap">
        <v-chip v-for="option in selectedOptions" :key="option.value" class="ma-1">
          {{ option.text }}
          <button class="pl-3" v-on:click="removeOption(option)">x</button>
        </v-chip>
      </div>
    </div>
    <v-list v-if="showOptions" v-for="option in filteredOptions" :key="option.value">
      <v-list-item @click="toggleOption(option)">{{ option.text }}</v-list-item>
    </v-list>
  </div>
</template>

<script>
export default {
  props: ['options'],
  data() {
    return {
      searchTerm: '',
      options: [
        {value: 1, text: 'Skill A'},
        {value: 2, text: 'Long Skill ABCDEFG'},
        {value: 3, text: 'Short H'},
        {value: 4, text: 'Skill I'},
        {value: 5, text: 'Skill J'},
      ],
      selectedOptions: [],
      showOptions: false,
    }
  },
  computed: {
    filteredOptions() {
      // Filter options based on search input and remove selected options
      return this.options.filter(option => !this.selectedOptions.includes(option) && option.text.toLowerCase().includes(this.searchTerm.toLowerCase()))
    },
  },
  methods: {
    clearInput() {
      this.searchTerm = ''
    },
    toggleOption(option) {
      const index = this.selectedOptions.indexOf(option)
      if (index === -1) {
        this.selectedOptions.push(option)
        this.clearInput()
      } else {
        this.selectedOptions.splice(index, 1)
      }
    },
    removeOption(option) {
      // Remove option from selected options array
      this.selectedOptions = this.selectedOptions.filter(selectedOption => selectedOption.value !== option.value)
    },
    toggleDropdown() {
      this.showOptions = !this.showOptions
    }
  },
}
</script>

<style>
.dropdown {
  position: relative;
  align-items: start;
}

.dropdown input {
  flex: 1;
  margin-right: 8px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 100%;
}
</style>
