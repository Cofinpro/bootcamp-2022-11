<template>
  <div class="d-flex flex-column">
    <div class="input-wrapper">
      <input v-model="searchTerm" class="input" @focus="showOptions=true" placeholder="Search options"/>
      <button class="btn btn-toggle-arrow" @click="toggleDropdown" v-on:click="toggleDropdown">
        <v-icon size="small">mdi-home</v-icon>
      </button>
    </div>
    <div class="dropdown">
      <ul v-if="showOptions" v-for="option in filteredOptions" :key="option.value">
        <li @click="toggleOption(option)">{{ option.text }}</li>
      </ul>
    </div>
  </div>
  <div class="d-flex flex-wrap">
    <v-chip v-for="option in selectedOptions" :key="option.value" class="ma-1">
      {{ option.text }}
      <button class="pl-3" v-on:click="removeOption(option)">x</button>
    </v-chip>
  </div>
</template>

<script>
export default {
  props: ['options'],
  data() {
    return {
      searchTerm: '',
      selectedOptions: [],
      showOptions: false,
    }
  },
  computed: {
    filteredOptions() {
      // Filter options based on search input and remove selected options
      return this.options.filter(option => !this.selectedOptions.includes(option)
          && option.text.toLowerCase().includes(this.searchTerm.toLowerCase()))
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
  position: absolute;
  background: #fff;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  min-width: 200px;
  max-width: 400px;
  z-index: 1;
}

.dropdown ul {
  margin: 0;
  padding: 0;
}

.dropdown li {
  list-style-type: none;
  padding: 8px;
  cursor: pointer;
}

.dropdown li:hover {
  background: #f8f8f8;
}

.input-wrapper {
  display: flex;
  position: relative;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.input {
  padding: 8px;
  flex: 1;
  border-style: hidden;
}

.btn {
  background: transparent;
  border: none;
  cursor: pointer;
  font-size: 16px;
  padding: 8px;
}

.btn-toggle-arrow {
  color: #666;
}

.btn-toggle-arrow:hover {
  color: #333;
}
</style>



