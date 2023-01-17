<template>
  <v-tooltip>
    <template v-slot:activator="{ props }">
      <v-btn id="button" elevation="0" :disabled="disabled"
             :style="disabled ? {
                  color: '#BDBDBD !important',
                  border: '1px dashed #BBBBBB !important',
                } : ''"
             min-width="40px" width="40px" height="35px"
             v-bind="props" @click="$emit('clicked')">
        <v-icon size="large">
          {{ icon }}
        </v-icon>
      </v-btn>
    </template>
    <span> {{ tooltip }}</span>
  </v-tooltip>
</template>

<script lang="ts">
export default {
  name: "ButtonWithTooltip",
  props: {
    tooltip: String,
    icon: String,
    disabled: true
  }
}
</script>

<style scoped>

#button {
  margin: 2%;
}
</style>
