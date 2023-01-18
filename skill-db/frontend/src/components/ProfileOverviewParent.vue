<template>
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
      <ImportButton @upload:csv="uploadCSV"/>
      <ExportButton @download:xlsx="downloadXLSX"/>
      <CreateButton @create:Profile="$router.push('/detail/new')"/>
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

  <v-container class="mt-2">
    <h3 class="message d-flex justify-center"
        v-if="overviewStore.cards.length === 0">
      Aktuell sind keine Profile hinterlegt.
    </h3>
    <v-row class="d-flex">
      <v-col v-for="card in overviewStore.cards"
             :key="card.getId()"
             cols="12" xl="2" lg="3" md="4" sm="6" xs="6"
             class="d-flex flex-sm-column align-center justify-center"
      >
        <OverviewCard :id="card.getId()"
                      :name="card.getName()"
                      :job-title="card.getJobTitle()"
                      :primary-skill="card.getPrimarySkill()"
                      :profile-pic="card.getProfilePic()"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import OverviewCard from "@/components/ProfileOverviewComponents/OverviewCard.vue";
import ButtonWithTooltip from "@/components/ProfileOverviewComponents/ButtonWithTooltip.vue";
import {useOverviewStore} from "@/stores/OverviewStore";
import ExportButton from "@/components/ProfileOverviewComponents/ExportButton.vue";
import ImportButton from "@/components/ProfileOverviewComponents/ImportButton.vue";
import CreateButton from "@/components/ProfileOverviewComponents/CreateButton.vue";
import {useBlobStore} from "@/stores/BlobStore";
import {useErrorStore} from "@/stores/ErrorStore";

export default {
  name: "OverviewParent",
  components: {ExportButton, ImportButton, CreateButton, OverviewCard, ButtonWithTooltip},
  setup() {
    const overviewStore = useOverviewStore();
    overviewStore.loadOverview();
    return {
      overviewStore
    };
  },
  methods: {
    filterProfiles() {
      console.log("Start filtering.");
    },
    async uploadCSV(file: File) {
      const blobStore = useBlobStore();
      await blobStore.postCSV(file);
      this.overviewStore.loadOverview();
    },
    async downloadXLSX() {
      const blobStore = useBlobStore();
      const errorStore = useErrorStore();
      await blobStore.getExcel();
      if (! errorStore.hasError) {
        const downloadUrl = window.URL.createObjectURL(blobStore.blob);
        const link = document.createElement('a');
        link.href = downloadUrl;
        link.setAttribute('download', 'profile.xlsx');
        document.body.appendChild(link);
        link.click();
        link.remove();
      }
    },
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

