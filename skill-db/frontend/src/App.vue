<template>
  <v-app id="inspire">
    <SideBar v-if="$route.path !== '/login'"/>

    <v-main>
      <router-view/>
      <ErrorSnackbar/>
    </v-main>

  </v-app>
</template>

<script setup lang="ts">
import SideBar from './components/SideBarComponents/SideBar.vue'
import ErrorSnackbar from "@/components/ErrorSnackbar.vue";
import {watch} from "vue";
import {useRoute} from "vue-router";

const route = useRoute();
watch(route, (to) => {
  document.title = to.meta.title || 'Website';
});
</script>
