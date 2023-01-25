<template>
  <RouterLink :to="to">
    <v-icon size="small" color="#BDBDBD" class="ml-8 mr-3">
      {{  icon }}
    </v-icon>
    {{ text }}
  </RouterLink>
</template>

<script setup lang="ts">
const name = "StyledLink";
const props = defineProps({
  to: {
    required: true,
  },
  icon: {
    required: true,
    type: String
  },
  text: {
    required: true,
    type: String
  }
});
</script>

<style scoped>

a {
  text-decoration: none;
  color: grey;
}
</style>