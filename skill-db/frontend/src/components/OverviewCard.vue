<template>
  <!--TODO card should shrink instead of overlapping -->
  <v-card height="165" max-width="255" min-width="255"
          @click="$router.push(`/detail/${id}`)"
          elevation="2" class="d-flex align-center justify-space-between"
  >
    <v-avatar size="60" class="ma-5">
      <img src="@/assets/images/dummy_profilePicture.png" alt="Profilbild">
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
  props: {
    id: Number,
    name: String,
    jobTitle: String,
    primarySkill: String,
  }
}
</script>

<style scoped>

#jobTitle {
  color: var(--primary-blue);
}

</style>