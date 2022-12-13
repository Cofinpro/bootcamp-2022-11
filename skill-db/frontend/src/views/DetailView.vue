<template>
  <DetailComponent :details="details"></DetailComponent>
  <v-btn><!-- @click ="showSettings">-->
    <v-icon color="--primary-orange" @click="settingIsVisible = !settingIsVisible">mdi-cog</v-icon>
    <v-card v-if="settingIsVisible">
      jsdfdsfsdf
    </v-card>
  </v-btn>
  <EditComponent></EditComponent>
</template>

<script lang="ts">
import {useDetailStore} from "@/stores/DetailStore";
import {DetailModel} from "@/models/DetailModel";
import DetailComponent from "@/components/DetailComponent.vue";
import EditComponent from "@/components/EditComponent.vue";

export default {
  components: {EditComponent, DetailComponent},
  setup() {
    const detailStore = useDetailStore();
    detailStore.loadDemoDetails();
    const details = detailStore.details;

    const settingIsVisible = false;

    return {
      details,
      settingIsVisible,
    };
  }
}
</script>

<style scoped>
.v-chip {
  margin-bottom: 10px;
}
</style>
