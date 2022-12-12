<template>
  <v-app id="inspire">
    <SideBar></SideBar>
    <RouterView/>
  </v-app>
</template>

<script lang="ts">
import SideBar from './components/SideBar.vue'
export default {
  components: {SideBar},
}
</script>

<style scoped>

</style>
