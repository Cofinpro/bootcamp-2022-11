<template>
  <div v-show="!overviewStore.loading" >
    <ProfileOverviewComponent/>
  </div>
</template>

<script setup lang="ts">
import {useOverviewStore} from "@/stores/OverviewStore";
import ProfileOverviewComponent from "@/components/ProfileOverviewComponents/ProfileOverviewComponent.vue";

const overviewStore = useOverviewStore();
overviewStore.loadOverview();
</script>
