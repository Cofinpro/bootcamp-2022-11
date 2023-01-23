<template>
  <img v-if="pic"
       v-bind:src="pic"
       alt="Profilbild"
       class="profilePic mr-3">
  <v-avatar v-else
            color="primary"
            size="170" rounded="0">
            <span class="text-h3">
             {{ initials[0] }}
             {{ initials[1] }}
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
    initials: {
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