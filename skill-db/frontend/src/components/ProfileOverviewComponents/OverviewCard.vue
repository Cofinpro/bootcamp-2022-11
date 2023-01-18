<template>
  <v-card height="165" :max-width="width" :min-width="width"
          @click="$router.push(`/detail/${id}`)"
          elevation="2" class="d-flex align-center justify-space-between">

    <v-avatar size="60" class="ma-2" color="primary">
      <img v-if="profilePic" :src="profilePic" alt="Profilbild">
      <div v-else>{{name[0]}}{{name.split(' ')[1][0]}}</div>
    </v-avatar>
    <div>
      <v-card-text>
        <p class="font-weight-bold">{{ name }}</p>
        <p id="jobTitle">{{ jobTitle }}</p>
        <p>{{ primarySkill }}</p>
      </v-card-text>
    </div>

  </v-card>
</template>

<script>
export default {
  name: "OverviewCard",
  props: {
    id: String,
    name: String,
    jobTitle: String,
    primarySkill: String,
    profilePic: String
  },
  computed: {
    width() {
      let size = this.$vuetify.display.width;
      switch (true) {
        case (size<600):
          return 275
        case (size<650):
          return 250
        case (size<960):
          return 275
        case (size<1050):
          return 200
        case (size<1200):
          return 225
        case (size<1280):
          return 250
        case (size<1500):
          return 225
        case (size<1900):
          return 275
        case (size<2100):
          return 250
        default:
          return 275
      }
    },
  },
}
</script>

<style scoped>
img{
  height: 80px;
}
#jobTitle {
  color: var(--primary-blue);
}
</style>
