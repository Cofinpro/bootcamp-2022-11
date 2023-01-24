<template>
  <v-chip @click="info = true"
          :color="color" link>
    {{ content }}
    <v-icon class="ml-2" size="tiny">
      mdi-information
    </v-icon>
  </v-chip>

  <v-overlay v-model="info" absolute>

  <v-card class="d-flex justify-space-between"
          elevation="2"
          :style="{ borderColor: color }"
          min-width="300" max-width="400">
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

<script lang="ts">
import {ref} from "vue";

export default {
  name: "ChipWithInfotext",
  props: {
    tooltip: {
      required: true,
      type: String
    },
    content: {
      required: true,
      type: String
    },
    color: {
      required: true,
      type: String
    }
  },
  setup() {
    return {
      info: ref(false),
    }
  },
}
</script>

<style scoped>

.v-card {
  margin-top: 20vh;
  margin-left: 20vw;
  margin-right: 2vw;
  border-top: 5px solid;
}
</style>