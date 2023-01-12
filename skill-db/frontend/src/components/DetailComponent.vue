<template>
  <div class="d-flex justify-end">
    <dropdown-button :functions="dropdownFunctions"/>
    <delete-profile-dialog v-model="toDelete" :functions="dialogFunctions"/>
    <v-overlay v-model="locked" absolute/>
  </div>

  <div class="header">
    <img v-if="detailStore.profilePic" v-bind:src="detailStore.profilePic" alt="Profilbild" class="profilePic">
    <v-avatar v-else color="primary" size="180" rounded="0" class="ma-4">
      <span class="text-h3">{{ detailStore.details.getFirstName()[0] }}{{ detailStore.details.getLastName()[0] }}</span>
    </v-avatar>
    <div class="header_content d-flex flex-column align-content-space-between">
      <div class="headline">
        <h1> {{ detailStore.details.getFirstName() }} {{ detailStore.details.getLastName() }}</h1>
        <h3> {{ detailStore.details.getJobTitle() }}, {{ detailStore.details.getAge() }}</h3>
      </div>

      <div class="infos">
        <div class="infos1 d-flex flex-column align-content-end">
          <p>
            <v-icon class="mr-3" size="small" color="#BDBDBD">
              mdi-email
            </v-icon>
            {{ detailStore.details.getEmail() }}
          </p>
          <p class="mt-3">
            <v-icon class="mr-3" size="small" color="#BDBDBD">
              mdi-crown-outline
            </v-icon>
            {{ detailStore.details.getPrimarySkill() }}
          </p>
        </div>
        <div class="infos2 d-flex flex-column">
          <p>
            <v-icon class="mr-3" size="small" color="#BDBDBD">
              mdi-phone
            </v-icon>
            {{ detailStore.details.getPhoneNumber() }}
          </p>
          <p class="mt-3">
            <v-icon class="mr-3" size="small" color="#BDBDBD">
              mdi-cake-variant-outline
            </v-icon>
            {{ detailStore.details.getBirthDate() }}
          </p>
        </div>
      </div>
    </div>
  </div>
  <v-container>
    <v-row class="lowerHalf pl-6 pr-6">
      <v-col cols="12" lg="4" md="6" sm="12">
          <v-card class="ml-n5"  elevation="3" style="border: 2px solid lightgray">
            <p class="block_title">
              Skills
            </p>
            <div class="d-flex flex-wrap">
              <div v-for="skill in detailStore.details.getTechnologies()" class="pa-1">
                <v-chip>{{ skill }}</v-chip>
              </div>
            </div>
          </v-card>
        <v-card class="mt-3 ml-n5" elevation="3" style="border: 2px solid lightgray">
          <div class="block_title">
            Abschluss
          </div>
          <div class="block_content">
            {{ detailStore.details.getDegree() }}
          </div>
        </v-card>
      </v-col>
      <v-col cols="12" lg="8" md="6" sm="12">
        <div class="ml-4">
          <div class="references pl-6 pt-2 ">
            <div class="block_title">
              Referenzen
            </div>
            <div class="block_content">
              <ul class="pl-6">
                <li v-for="reference in detailStore.details.getReferences().split(',')">
                  <p>{{ reference }}</p>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import DropdownButton from "@/components/DropdownButton.vue";
import DeleteProfileDialog from "@/components/DeleteProfileDialog.vue";
import {useDetailStore} from "@/stores/DetailStore";
import {useRoute} from "vue-router";
import {ref} from "vue";
import router from "@/router";

export default {
  name: "DetailComponent",
  components: {DropdownButton, DeleteProfileDialog},
  setup() {
    const detailStore = useDetailStore();
    const id = String(useRoute().params.id);
    detailStore.loadDetailsById(id);

    const locked = ref(false);
    const toDelete = ref(false);

    function enterEdit(): void {
      router.push({name: 'editView', params: {id: id}});
    }

    function toggleDelete(): void {
      toDelete.value = !toDelete.value;
    }

    function lockProfile(): void {
      locked.value = true;
      console.log("This profile is now locked away.");
      /*router.push(`/`);*/
    }

    function deleteProfile(): void {
      detailStore.deleteDetailsByID(id);
      router.push(`/`);
    }

    return {
      dropdownFunctions: [
        {name: 'Bearbeiten', method: enterEdit},
        {name: 'Löschen', method: toggleDelete},
      ],
      dialogFunctions: [
        {name: 'Ja', method: deleteProfile},
        {name: 'Nein', method: toggleDelete}
      ],
      detailStore, toDelete, locked
    };
  }
}
</script>

<style scoped>

.header {
  width: 100%;
  display: flex;
}

.profilePic {
  height: 200px;
}

.headline {
  height: 100px;
  margin-left: 10%;
}

.infos {
  display: flex;
  justify-content: space-between;
  font-size: 11pt;
  height: 100px;
  margin-left: 10%;
  margin-top: 60px;
}

.header_content {
  height: 200px;
  width: 700px;
}

.lowerHalf {
  margin-top: 5%;
}

.content_card {
  border-radius: 8px;
  padding: 8px 12px;
  border-color: rgba(217, 217, 217, 1);
  border-width: 1px;
  border-style: solid;
}

.references {
  margin-left: -42px;
}

.block_title {
  font-size: 16px;
  font-weight: bold;
  line-height: 20px;
  letter-spacing: 2px;
  text-align: left;
  font-family: "Poppins", sans-serif;
}

.block_content {
  margin-top: 12px;
  font-size: 16px;
  text-align: left;
  font-family: "Poppins", sans-serif;
}

@media screen and (max-width: 960px) {
  .header {
    height: 480px;
    margin: auto;
    display: grid;
    grid-template-rows: 1fr 1fr 0.8fr;
    grid-row: 2 / span 1;
  }

  img {
    display: grid;
    grid-row: 1 / span 1;
    margin-left: 0;
  }

  .headline {
    display: grid;
    grid-row: 2 / span 1;
    margin-left: 0;
    margin-top: 5%;
  }

  .infos {
    display: grid;
    margin-left: 0;
    margin-top: 5%;
    height: 150px;
    width: 400px;
  }

  .infos2 {
    margin-top: 10px;
  }

  .lowerHalf {
    margin-top: 40px;
  }
}
</style>
