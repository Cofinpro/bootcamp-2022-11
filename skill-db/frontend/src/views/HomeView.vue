<template>
  <div v-if="!overviewStore.loading">
    <div class="headline">
      <h3>Profilübersicht</h3>
      <h4 class="font-weight-regular">Übersicht über alle vorhandenen Profile</h4>
    </div>

    <div class="searchAndButtons d-flex justify-end align-center">
      <v-card v-if="false" class="searchbar d-flex justify-center"
              color="grey" width="45%" height="20%">
        Searchbar
      </v-card>
      <div class="d-flex justify-space-between">
        <ImportButton/>
        <ExportButton/>
        <ButtonWithTooltip tooltip="Neues Profil erstellen"
                           icon="mdi-plus-thick"
                           @clicked="createProfile"/>
        <ButtonWithTooltip v-if="false"
                           tooltip="Filter"
                           icon="mdi-filter"
                           @clicked="filterProfiles"/>
      </div>
    </div>

    <div v-if="false" class="activeFilter">
      <h5>Aktive Filter:</h5>
      <div class="mt-2">
        <v-chip class="mr-3">Filter1</v-chip>
        <v-chip>Filter2</v-chip>
      </div>
    </div>

    <v-container class="cards mt-2 ml-0">
      <h3 class="message d-flex justify-center"
          v-if="overviewStore.cards.length === 0">
        Aktuell sind keine Profile hinterlegt.
      </h3>
      <v-row>
        <v-col v-for="card in overviewStore.cards" :key="card.getId()"
               cols="12" lg="3" md="4" sm="6">
          <OverviewCard :id="card.getId()"
                        :name="card.getName()"
                        :job-title="card.getJobTitle()"
                        :primary-skill="card.getPrimarySkill()"
                        :profile-pic="card.getProfilePic()"/>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script lang="ts">
import OverviewCard from "@/components/OverviewCard.vue";
import ButtonWithTooltip from "@/components/ButtonWithTooltip.vue";
import {useOverviewStore} from "@/stores/OverviewStore";
import router from "@/router";
import ExportButton from "@/components/ExportButton.vue";
import ImportButton from "@/components/ImportButton.vue";

export default {
  name: "HomeView",
  components: {ExportButton, ImportButton, OverviewCard, ButtonWithTooltip },
  setup() {
    const overviewStore = useOverviewStore();
    overviewStore.loadOverview();
    return {
      overviewStore
    };
  },
  methods: {
    createProfile() {
      console.log("Now you can create a new profile.");
      router.push(`/details/new`);
    },
    filterProfiles() {
      console.log("Start filtering.");
    }
  }
}
</script>

<style>

body {
  margin: 4%;
}

h4 {
  margin-top: 1%;
}

h3.message {
  color: #BDBDBD;
}

.searchAndButtons {
  margin-top: 4%;
}

.activeFilter {
  margin-top: 1%;
}

</style>
