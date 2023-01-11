<template>
  <v-app id="inspire">
    <SideBar v-if="$route.path !== '/login'"/>

    <v-main>
      <router-view/>
      <ErrorSnackbar/>
    </v-main>

  </v-app>
</template>

<script lang="ts">
import SideBar from './components/SideBar.vue'
import ErrorSnackbar from "@/components/ErrorSnackbar.vue";
export default {
  components: { ErrorSnackbar, SideBar },
  watch: {
    '$route' (to, from) {
      document.title = to.meta.title || 'Website'
    }
  }
}
</script>

<style scoped>
</style>
