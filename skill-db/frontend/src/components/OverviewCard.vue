<template>
  <v-card height="165" max-width="255" min-width="200"
          @click="$router.push(`/detail/${id}`)"
          elevation="2" class="d-flex align-center justify-space-between">

    <v-avatar size="60" class="ma-3" color="primary">
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

<script lang="ts">
export default {
  name: "OverviewCard",
  props: {
    id: String,
    name: String,
    jobTitle: String,
    primarySkill: String,
    profilePic: String
  }
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
