<template>
  <Suspense>
    <UserComponent/>
  </Suspense>
</template>

<script setup lang="ts">
import UserComponent from "@/components/UserComponents/UserComponent.vue";
</script>
