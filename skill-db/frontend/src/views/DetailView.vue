<template>
  <div class="d-flex justify-end">
  <v-menu :close-on-content-click="false">
    <template v-slot:activator="{ props }">
      <v-btn v-bind="props"
             min-width="40px" width="40px" height="35px"
             class="pa-0" elevation="0">
        <v-icon size="large">
          mdi-cog
        </v-icon>
      </v-btn>
    </template>
    <v-list>
      <v-list-item link @click="enterEdit">
        <v-list-item-title> Bearbeiten</v-list-item-title>
      </v-list-item>
      <v-list-item link @click.stop="toggleDelete">
        <v-list-item-title> Löschen</v-list-item-title>
      </v-list-item>
      <v-list-item link @click="lockProfile">
        <v-list-item-title> Sperren</v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>

  <v-dialog v-model="toDelete" max-width="200">
    <v-card>
      <v-card-text>Willst du dieses Profil wirklich löschen?</v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn @click="deleteProfile">
          Ja
        </v-btn>
        <v-btn @click="toggleDelete" class="ml-2">
          Nein
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
  <v-overlay v-model="locked" absolute/>
  </div>

  <div class="header">
    <img src="@/assets/images/dummy_profilePicture.png" alt="Profilbild">
    <div class="header_content d-flex flex-column align-content-space-between">
      <div class="headline">
        <h1> {{ detail.getFirstName() }} {{ detail.getLastName() }}</h1>
        <h3> {{ detail.getJobTitle() }}, {{ detail.getAge() }}</h3>
      </div>

      <div class="infos">
        <div class="infos1 d-flex flex-column align-content-end">
          <p>
            <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-email</v-icon>
            max.mustermann@cofinpro.de
          </p>
          <p class="mt-3">
            <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-crown-outline</v-icon>
            {{ detail.getPrimarySkill() }}
          </p>
        </div>
        <div class="infos2 d-flex flex-column">
          <p>
            <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-phone</v-icon>
            +49 176 65544 000
          </p>
          <p class="mt-3">
            <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-cake-variant-outline</v-icon>
            {{ detail.getBirthDate() }}
          </p>
        </div>
      </div>
    </div>
  </div>

  <v-row class="pl-6 pr-6 mt-10 mt-md-15 mt-sm-5">
    <v-col lg="6" md="6" sm="12" xs ="12">
        <div class="content_card d-flex">
          <p class="block_title">Skills</p>
          <div class="d-flex">
            <div v-for="skill in detail.getTechnologies()" class="pa-1 flex-wrap">
              <v-chip>{{ skill }}</v-chip>
            </div>
          </div>
        </div>
        <div class="content_card mt-5">
          <div class="block_title">Abschluss</div>
          <div class="block_content">
            B.Sc. Wirtschaftinformatik
          </div>
        </div>
    </v-col>
    <v-col>
      <div class="references pl-6 pt-2">
        <div class="block_title">Referenzen</div>
        <div class="block_content">
          <ul class="pl-6">
            <li v-for="reference in detail.getReferences().split(',')">
              <p>{{ reference }}</p>
            </li>
          </ul>
        </div>
      </div>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import {useDetailStore} from "@/stores/DetailStore";
import DetailComponent from "@/components/DetailComponent.vue";
import {ref} from "vue";
import router from "@/router";

export default {
  components: {DetailComponent},
  setup() {
    const detailStore = useDetailStore();
    detailStore.loadDemoDetails();
    /*detailStore.loadDetailsById(ID); TODO how are we getting the ID here?*/
    const detail = detailStore.details;

    const locked = ref(false);
    const toDelete = ref(false);

    function enterEdit(): void {
      router.push('/detail/edit/1');
      /*router.push({ name: 'editView', params: { id: detail.getId()}});*/
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
      /*detailStore.deleteDetailsByID(detail.getId()); TODO server/backend problems*/
      router.push(`/`);
    }

    return {
      detail, toDelete, locked,
      enterEdit, toggleDelete,
      lockProfile, deleteProfile
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
    height: 50%;
    margin: auto;
    display: grid;
    grid-template-rows: 1fr 0.5fr 0.5fr 0.5fr;;
  }
  img {
    grid-row: 0;
    margin-left: 0;
  }
  .headline {
    grid-row: 1;
    margin-left: 0;
    margin-top: 5%;
  }
  .infos {
    display: grid;
    margin-left: 0;
    margin-top: 5%;
  }
  .infos2 {
    margin-top: 10px;
  }
}

</style>
