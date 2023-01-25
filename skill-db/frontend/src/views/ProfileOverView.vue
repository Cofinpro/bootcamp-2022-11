<template>
  <div v-show="!overviewStore.loading" >
    <ProfileOverviewComponent/>
  </div>
</template>

<script setup lang="ts">
import {useOverviewStore} from "@/stores/OverviewStore";
import ProfileOverviewComponent from "@/components/ProfileOverviewComponent.vue";

const name = "ProfileOverView";
const overviewStore = useOverviewStore();
overviewStore.loadOverview();
</script>
