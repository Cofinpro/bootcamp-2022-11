<template>
  <Suspense>
    <DetailComponent/>
  </Suspense>
</template>

<script setup lang="ts">
import DetailComponent from "@/components/DetailComponents/DetailComponent.vue";

const name = "DetailView";
</script>
