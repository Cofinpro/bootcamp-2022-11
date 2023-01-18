<template>
  <p class="text-no-wrap">
    <v-icon class="mr-3" size="small" color="#BDBDBD">
      {{ icon }}
    </v-icon>
    {{ info }}
  </p>
</template>

<script>
export default {
  name: "InfoWithIcon",
  props: {
    icon: {
      required: true,
      type: String
    },
    info: {
      required: true,
      type: String
    }
  }
}
</script>

<style scoped>
</style>