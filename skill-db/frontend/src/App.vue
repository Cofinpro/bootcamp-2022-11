<template>
  <v-app id="inspire">
    <SideBar v-if="$route.path !== '/login'"></SideBar>

    <v-main>
      <router-view/>
    </v-main>

  </v-app>
</template>

<script lang="ts">
import SideBar from './components/SideBar.vue'
import router from "@/router";
export default {
  components: {SideBar},
}
</script>

<style scoped>

</style>
