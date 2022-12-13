<template>
  <div class="headline">
    <h1>Profilübersicht</h1>
    <h2>Übersicht über alle vorhandenen Profile</h2>
  </div>
  <v-btn elevation="0"
         min-width="40px" width="40px" height="35">
    <v-icon size="large" @click="createProfile"> mdi-plus-thick </v-icon>
  </v-btn>
  <v-container>
    <v-row>
      <v-col v-for="card in cardList" :key="card.getId()" lg="4" elevation="5" md="6">
        <OverviewCard :id="card.getId()"
                      :name="card.getName()"
                      :job-title="card.getJobTitle()"
                      :primary-skill="card.getPrimarySkill()"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import OverviewCard from "@/components/OverviewCard.vue";
import {useOverviewStore} from "@/stores/OverviewStore";
import router from "@/router";

export default {
  components: {OverviewCard},
  setup() {
    const overviewStore = useOverviewStore();
    overviewStore.loadAllOverviews();
    const cardList = overviewStore.cards
    return {
      cardList,
    };
  },
  methods: {
    createProfile() {
      console.log("Now you can create a new profile.");
      router.push(`/detail/new`);

    }
  }
}
</script>
