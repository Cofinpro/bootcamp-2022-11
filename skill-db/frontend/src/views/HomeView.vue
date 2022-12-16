<template>
  <div class="headline">
    <h3>Profilübersicht</h3>
    <h4 class="font-weight-regular">Übersicht über alle vorhandenen Profile</h4>
  </div>

  <div class="searchAndButtons d-flex justify-space-between align-center">
    <v-card class="searchbar d-flex justify-center" color="grey" width="45%" height="20%">Searchbar</v-card>
    <div class="d-flex justify-space-between">
      <ButtonWithTooltip tooltip="Liste exportieren" icon="mdi-file-export" @clicked="exportProfiles"/>
      <ButtonWithTooltip tooltip="Neues Profil erstellen" icon="mdi-plus-thick" @clicked="createProfile"/>
      <ButtonWithTooltip tooltip="Filter" icon="mdi-filter" @clicked="filterProfiles"/>
    </div>
  </div>

  <div class="activeFilter">
    <h5>Aktive Filter:</h5>
    <div class="mt-2">
      <v-chip class="mr-3">Filter1</v-chip>
      <v-chip>Filter2</v-chip>
    </div>
  </div>

  <v-container class="cards mt-2">
    <v-row>
      <v-col v-for="card in cardList" :key="card.getId()" lg="3" md="4" sm="6" xs ="12">
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
import ButtonWithTooltip from "@/components/ButtonWithTooltip.vue";
import {useOverviewStore} from "@/stores/OverviewStore";
import router from "@/router";

export default {
  components: {OverviewCard, ButtonWithTooltip},
  setup() {
    const overviewStore = useOverviewStore();
    /*overviewStore.loadOverview();*/
    overviewStore.loadDummyOverview();
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
      console.log("Here you can export the shown profile to xlsx");
    },
    filterProfiles() {
      console.log("Start filtering.");
    }
  },
  metaInfo: {
    title: "Overview",
  }
}
</script>

<style>
body {
  margin: 5%;
}

h4 {
  margin-top: 1%;
}

.searchAndButtons {
  margin-top: 4%;
}

.activeFilter {
  margin-top: 1%;
}
</style>
