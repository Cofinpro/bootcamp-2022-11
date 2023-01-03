<template>
  <v-menu :close-on-content-click="false">
    <template v-slot:activator="{ props }">
      <v-btn class="pa-0" v-bind="props"
             min-width="40px" width="40px" height="35px"
             elevation="0">
        <v-icon size="large">
          mdi-cog
        </v-icon>
      </v-btn>
    </template>

    <v-list>
      <v-list-item v-for="func in functions" link
                   :key="func.name" @click="func.method">
        <v-list-item-title>
          {{ func.name }}
        </v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>
</template>

<script lang="ts">
export default {
  name: "DropdownButton",
  props: {
    functions: {
      type: Array,
      required: true,
    }
  }
}
</script>

<style scoped>
</style>