<template>
  <p class="text-no-wrap">
    <v-icon class="mr-3" size="small" color="#BDBDBD">
      {{ icon }}
    </v-icon>
    {{ info }}
  </p>
</template>

<script setup lang="ts">
const props = defineProps({
  icon: {
    required: true,
    type: String
  },
  info: {
    required: true,
    type: String
  }
});
</script>