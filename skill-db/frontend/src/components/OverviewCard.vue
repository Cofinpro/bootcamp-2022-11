<template>
  <v-card height="220" min-width="400" max-width="400" @click="$router.push(`/detail/${id}`)">
    <v-card-title>
      {{ name }}
    </v-card-title>
    <v-card-text>
      {{ jobTitle }}
    </v-card-text>
    <v-card-text>
      {{ primarySkill }}
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  props: {
    id: Number,
    name: String,
    jobTitle: String,
    primarySkill: String,
  }
}
</script>

<style scoped>

</style>