<template>
  <v-app id="inspire">
    <SideBar v-if="$route.path !== '/login'"/>

    <v-main>
      <router-view/>
    </v-main>

  </v-app>
</template>

<script lang="ts">
import SideBar from './components/SideBar.vue'
export default {
  components: {SideBar},
  watch: {
    '$route' (to, from) {
      document.title = to.meta.title || 'Website'
    }
  }
}
</script>

<style scoped>
</style>
