<template>
  <v-tooltip top>
    <template v-slot:activator="{ props }">
      <v-btn elevation="0" class="ma-2"
             min-width="40px" width="40px" height="35"
             v-bind="props"
      >
        <v-icon size="large" @click="$emit('clicked')"> {{ icon }} </v-icon>
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

</style>