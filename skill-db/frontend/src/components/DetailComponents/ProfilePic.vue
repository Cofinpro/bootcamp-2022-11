<template>
  <img v-if="pic"
       v-bind:src="pic"
       alt="Profilbild"
       class="profilePic mr-3">
  <v-avatar v-else
            color="primary"
            size="170" rounded="0">
            <span class="text-h3">
             {{ firstName[0] }}
             {{ lastName[0] }}
            </span>
  </v-avatar>
</template>

<script setup lang="ts">
const name = "ProfilePic";
const props = defineProps({
  pic: {
    required: false,
    default: undefined
  },
  firstName: {
    required: false,
    default: undefined,
    type: String
  },
  lastName: {
    required: false,
    default: undefined,
    type: String
  }
});
</script>

<style scoped>

.profilePic {
  height: 200px;
}
</style>