<template>
  <div class="headline">
    <h1>Profilübersicht</h1>
    <h2>Übersicht über alle vorhandenen Profile</h2>
  </div>
  <div class="searchAndButtons">
    <v-card color="grey" width="45%" height="20%">Searchbar</v-card>

    <v-tooltip top>
      <template v-slot:activator="{ props }">
        <v-btn elevation="0" class="ma-2"
               min-width="40px" width="40px" height="35"
               v-bind="props"
        >
          <v-icon size="large" @click="exportProfiles"> mdi-file-export </v-icon>
        </v-btn>
      </template>
      <span>Liste exportieren</span>
    </v-tooltip>

    <v-tooltip bottom>
      <template v-slot:activator="{ props }">
        <v-btn elevation="0" class="ma-2"
               min-width="40px" width="40px" height="35"
               v-bind="props"
        >
          <v-icon size="large" @click="createProfile"> mdi-plus-thick </v-icon>
        </v-btn>
      </template>
      <span>Neues Profil erstellen</span>
    </v-tooltip>

    <v-tooltip left>
      <template v-slot:activator="{ props }">
        <v-btn elevation="0" class="ma-2"
               min-width="40px" width="40px" height="35"
               v-bind="props"
        >
          <v-icon size="large" @click="filterProfiles"> mdi-filter </v-icon>
        </v-btn>
      </template>
      <span>Filter</span>
    </v-tooltip>

  </div>

  <div class="activeFilter">

    <h3>Aktive Filter:</h3>
    <v-chip class="ma-2">
      Filter1</v-chip>
    <v-chip>Filter2</v-chip>


  </div>

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
      router.push(`/details/new`);
    },
    exportProfiles() {
      console.log("Here you can export the shown profile to xlsx")
    },
    filterProfiles() {
      console.log("Start filtering.");
    }
  }
}
</script>
