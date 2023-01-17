<template>
  <v-btn class="mt-10 ml-lg-5 ml-md-5"
         @click="this.$emit('click')"
         elevation="0">
    Abbrechen
  </v-btn>
</template>

<script>
export default {
  emits: ['click'],
  name: "LeaveButton",
}
</script>

<style scoped>

</style>
