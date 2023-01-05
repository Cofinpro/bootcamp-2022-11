<template>
  <v-chip @click="info = true"
          :color="color">
    {{ content }}
    <v-icon class="ml-2" size="tiny">
      mdi-information
    </v-icon>
  </v-chip>
  <v-overlay v-model="info" absolute>
  <v-card class="d-flex justify-space-between" elevation="2"
          :style="{ borderColor: color }">
    <v-card-text>
      {{ tooltip }}
    </v-card-text>
    <v-card-actions class="d-flex justify-end">
      <v-icon class="mr-5" @click="info = false">
        mdi-close-circle-outline
      </v-icon>
    </v-card-actions>
  </v-card>
  </v-overlay>
</template>

<script>
export default {
  name: "ChipWithTooltip",
  props: {
    tooltip: String,
    content: String,
    color: String
  },
  data() {
    return {
      info: false,
    }
  },
}
</script>

<style scoped>
.v-card {
  max-width: 400px;
  margin-top: 15vh;
  margin-left: 50vw;
  border-top: 5px solid;
}
</style>