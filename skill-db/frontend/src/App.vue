<template>
  <v-app id="bootcamp">


    <v-main>
        <router-view/>
    </v-main>
  </v-app>


</template>

<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
</script>

<style scoped>
</style>
