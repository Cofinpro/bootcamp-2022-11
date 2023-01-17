<template>
  <v-dialog max-width="200">
    <v-card>
      <v-card-text>Willst du dieses Profil wirklich löschen?</v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn v-for="func in functions" class="ml-1 mr-1" @click="func.method">
          {{ func.name }}
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
export default {
  name: 'DeleteProfileDialog',
  props: {
    functions: {
      type: Array,
      required: true,
    }
  },
}
</script>

<style scoped>

img {
  height: 200px;
}

@media screen and (max-width: 1050px) {
  img {
    display: grid;
    grid-row: 1 / span 1;
    margin-left: 0;
  }
}
</style>
