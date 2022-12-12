<template>
  <div class="headline">
    <h1>Profilübersicht</h1>
    <h2>Übersicht über alle vorhandenen Profile</h2>
  </div>
  <v-btn elevation="1" outlined>
    Neues Profil erstellen
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

export default {
  components: {OverviewCard},
  setup() {
    const overviewStore = useOverviewStore();
    overviewStore.loadAllOverviews();
    const cardList = overviewStore.cards
    return {
      cardList,
    };
  }
}
</script>
