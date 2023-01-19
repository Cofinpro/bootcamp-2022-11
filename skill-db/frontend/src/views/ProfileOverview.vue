<template>
  <div v-show="!overviewStore.loading" >
    <ProfileOverviewParent/>
  </div>
</template>

<script lang="ts">
import {useOverviewStore} from "@/stores/OverviewStore";
import ProfileOverviewComponent from "@/components/ProfileOverviewComponent.vue";

export default {
  name: "HomeView",
  components: {ProfileOverviewComponent},
  setup() {
    const overviewStore = useOverviewStore();
    overviewStore.loadOverview();
    return {
      overviewStore
    };
  },
}
</script>

<style>


</style>
