<template>
  <div v-show="!overviewStore.loading" >
    <ProfileOverviewParent/>
  </div>
</template>

<script lang="ts">
import {useOverviewStore} from "@/stores/OverviewStore";
import ProfileOverviewParent from "@/components/ProfileOverviewParent.vue";

export default {
  name: "HomeView",
  components: {ProfileOverviewParent},
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
