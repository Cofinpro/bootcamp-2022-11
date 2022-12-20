<template>
  <v-row class="header">
    <v-col cols="3">
      <div class="icon"></div>
    </v-col>
    <v-col class="d-flex flex-column header_content">
      <v-row class="h-100">
        <v-col>
          <h1> {{ detail.getFirstName() }} {{ detail.getLastName() }}</h1>
          <h3>{{ detail.getJobTitle() }}, {{ detail.getAge() }}</h3>
        </v-col>
        <v-col cols="1">
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
        </v-col>
      </v-row>
      <div class="d-flex w-66 justify-space-between">
        <div class="d-flex flex-column">
          <p>
            <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-email</v-icon>
            max.mustermann@cofinpro.de
          </p>
          <p class="mt-3">
            <v-icon size="small" color="#BDBDBD" class="mr-3">mdi-crown-outline</v-icon>
            {{ detail.getPrimarySkill() }}
          </p>
        </div>
        <div class="d-flex flex-column">
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
    </v-col>
  </v-row>
  <v-row class="pl-6 pr-6">
    <v-col cols="3">
      <div>
        <div class="content_card">
          <div class="block_title">Skills</div>
          <SearchableMultiSelectDropdown :options="myOptions"/>
          <div class="d-flex">
            <p v-for="skill in detail.getTechnologies()" class="pa-1 flex-wrap">
              <v-chip>{{ skill }}</v-chip>
            </p>
          </div>
        </div>
        <div class="content_card mt-5">
          <div class="block_title">Abschluss</div>
          <div class="block_content">
            B.Sc. Wirtschaftinformatik
          </div>
        </div>
      </div>
    </v-col>
    <v-col class="w-100">
      <div class="pl-6 pt-2">
        <div class="block_title">Referenzen</div>
        <div class="block_content">
          <ul class="pl-6">
            <li v-for="reference in detail.getReferences()">
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

  data() {
    return {
      myOptions: [
        {value: 1, text: 'Option 1'},
        {value: 2, text: 'Option 2'},
        {value: 3, text: 'Option 3'}
      ],
    }
  },
}
</script>

<style scoped>
.header {
  display: flex;
  max-width: 100%;
  justify-content: flex-start;
  align-items: flex-start;
  background-color: rgba(255, 255, 255, 1);
}

.header_content {
  height: 256px;
}

.icon {
  display: flex;
  height: 256px;
  border-radius: 12px;
  background-color: rgba(196, 196, 196, 1);
}

.content_card {
  display: flex;
  flex-direction: column;
  max-width: 100%;
  justify-content: flex-start;
  align-items: flex-start;
  border-radius: 8px;
  padding: 8px 12px;
  border-color: rgba(217, 217, 217, 1);
  border-width: 1px;
  border-style: solid;
  background-color: rgba(255, 255, 255, 1);
  width: 100%;
}

.block_title {
  max-width: 100%;
  color: rgba(0, 0, 0, 1);
  font-size: 16px;
  font-weight: bold;
  line-height: 20px;
  letter-spacing: 4px;
  text-align: left;
  font-family: "Poppins", sans-serif;
  width: 100%;
}

.block_content {
  margin-top: 12px;
  font-size: 16px;
  text-align: left;
  font-family: "Poppins", sans-serif;
}

</style>
