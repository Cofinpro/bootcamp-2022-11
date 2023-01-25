<template>
    <div class="ma-10 pa-5">
      <v-btn elevation="0" @click="this.$emit('click')">
        logout
        <v-icon class="ml-3">
          mdi-exit-to-app
        </v-icon>
      </v-btn>
    </div>
</template>

<script setup lang="ts">
const name = "LogoutButton";
</script>