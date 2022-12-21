<template>
  <v-tooltip>
    <template v-slot:activator="{ props }">
      <v-btn id="button" elevation="0"
             min-width="40px" width="40px" height="35"
             v-bind="props" @click="$emit('clicked')"
      >
        <v-icon size="large"> {{ icon }} </v-icon>
      </v-btn>
    </template>
    <span> {{ tooltip }}</span>
  </v-tooltip>
</template>

<script>
export default {
  props: {
    tooltip: String,
    icon: String,
  }
}
</script>

<style scoped>
  #button {
    margin: 2%;
  }
</style>