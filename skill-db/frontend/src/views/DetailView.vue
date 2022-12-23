<template>
  <div v-if="!detailStore.loading">
    <div class="d-flex justify-end">
      <DropdownButton :functions="dropdownFunctions"/>
      <DeleteProfileDialog v-model="toDelete" :functions="dialogFunctions"/>
      <v-overlay v-model="locked" absolute/>
    </div>

    <div class="header">
      <img src="@/assets/images/dummy_profilePicture.png" alt="Profilbild">
      <div class="header_content d-flex flex-column align-content-space-between">
        <div class="headline">
          <h1> {{ detailStore.details.getFirstName() }} {{ detailStore.details.getLastName() }}</h1>
          <h3> {{ detailStore.details.getJobTitle() }}, {{ detailStore.details.getAge() }}</h3>
        </div>

        <div class="infos">
          <div class="infos1 d-flex flex-column align-content-end">
            <p>
              <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-email</v-icon>
              {{ detailStore.details.getEmail() }}
            </p>
            <p class="mt-3">
              <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-crown-outline</v-icon>
              {{ detailStore.details.getPrimarySkill() }}
            </p>
          </div>
          <div class="infos2 d-flex flex-column">
            <p>
              <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-phone</v-icon>
              {{ detailStore.details.getPhoneNumber() }}
            </p>
            <p class="mt-3">
              <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-cake-variant-outline</v-icon>
              {{ detailStore.details.getBirthDate() }}
            </p>
          </div>
        </div>
      </div>
    </div>

    <v-row class="lowerHalf pl-6 pr-6">
      <v-col cols="12" lg="6" md="6" sm="12">
        <div class="content_card d-flex">
          <p class="block_title">Skills</p>
          <div class="d-flex flex-wrap">
            <div v-for="skill in detailStore.details.getTechnologies()" class="pa-1">
              <v-chip>{{ skill }}</v-chip>
            </div>
          </div>
        </div>
        <div class="content_card mt-5">
          <div class="block_title">Abschluss</div>
          <div class="block_content">
            {{ detailStore.details.getDegree() }}
          </div>
        </div>
      </v-col>
      <v-col cols="12" lg="6" md="6" sm="12">
        <div class="references pl-6 pt-2">
          <div class="block_title">Referenzen</div>
          <div class="block_content">
            <ul class="pl-6">
              <li v-for="reference in detailStore.details.getReferences().split(',')">
                <p>{{ reference }}</p>
              </li>
            </ul>
          </div>
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<script lang="ts">
import {useDetailStore} from "@/stores/DetailStore";
import {ref} from "vue";
import router from "@/router";
import {useRoute} from "vue-router";
import DropdownButton from "@/components/DropdownButton.vue";
import DeleteProfileDialog from "@/views/DeleteProfileDialog.vue";

export default {
  components: {DeleteProfileDialog, DropdownButton},
  setup() {
    const detailStore = useDetailStore();
    const id = Number(useRoute().params.id);
    detailStore.loadDetailsById(id);
    const detail = detailStore.details;

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
        {name: 'Sperren', method: lockProfile},
      ],
      dialogFunctions: [
        {name: 'Ja', method: deleteProfile},
        {name: 'Nein', method: toggleDelete}
      ],
      detail, detailStore, toDelete, locked
    };
  },

}
</script>

<style scoped>

.header {
  width: 100%;
  display: flex;
}

img {
  height: 200px;
}

.headline {
  height: 100px;
  margin-left: 10%;
}

.infos {
  display: flex;
  justify-content: space-between;
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
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  border-radius: 8px;
  padding: 8px 12px;
  border-color: rgba(217, 217, 217, 1);
  border-width: 1px;
  border-style: solid;
  width: 300px;
  margin-left: -30px;
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

@media screen and (max-width: 1050px) {
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
