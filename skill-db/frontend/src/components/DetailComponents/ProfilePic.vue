<template>
  <img v-if="pic"
       v-bind:src="pic"
       alt="Profilbild"
       class="profilePic mr-3">
  <v-avatar v-else
            color="primary"
            size="170" rounded="0">
            <span class="text-h3">
             {{ initals[0] }}
             {{ initals[1] }}
            </span>
  </v-avatar>
</template>

<script>
export default {
  name: "ProfilePic",
  props: {
    pic: {
      required: false,
      default: ''
    },
    initals: {
      required: false,
      default: '',
      type: String
    }
  }
}
</script>

<style scoped>

.profilePic {
  height: 200px;
}
</style>