<template>
  <div class="headline">
    <h3>Profilübersicht</h3>
    <h4>Übersicht über alle vorhandenen Profile</h4>
  </div>

  <div class="searchAndButtons">
    <v-card id="searchbar" color="grey" width="45%" height="20%">Searchbar</v-card>
    <ButtonWithTooltip tooltip="Liste exportieren" icon="mdi-file-export" @clicked="exportProfiles"/>
    <ButtonWithTooltip tooltip="Neues Profil erstellen" icon="mdi-plus-thick" @clicked="createProfile"/>
    <ButtonWithTooltip tooltip="Filter" icon="mdi-filter" @click="filterProfiles"/>
  </div>

  <div class="activeFilter">
    <h5>Aktive Filter:</h5>
    <v-chip class="mt-3 mr-3">Filter1</v-chip>
    <v-chip class="mt-3">Filter2</v-chip>
  </div>

  <v-container class="cards">
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
import ButtonWithTooltip from "@/components/ButtonWithTooltip.vue";
import {useOverviewStore} from "@/stores/OverviewStore";
import router from "@/router";

export default {
  components: {OverviewCard, ButtonWithTooltip},
  setup() {
    const overviewStore = useOverviewStore();
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
      console.log("Here you can export the shown profile to xlsx")
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
