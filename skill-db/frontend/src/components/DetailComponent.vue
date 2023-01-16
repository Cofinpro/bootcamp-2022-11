<template>
  <div class="pt-md-14">
    <div style="float: right">
      <dropdown-button :functions="dropdownFunctions"/>
      <delete-profile-dialog v-model="toDelete" :functions="dialogFunctions"/>
      <v-overlay v-model="locked" absolute/>
    </div>
    <v-container class="pr-0 pl-2 pl-sm-2 pl-md-4">
      <v-row class="pl-n6">
        <v-col cols="12" lg="4" md="4" sm="12" class="d-flex justify-md-center">
          <img v-if="detailStore.profilePic" v-bind:src="detailStore.profilePic" alt="Profilbild"
               class="profilePic mr-3">
          <v-avatar v-else color="primary" size="170" rounded="0">
        <span class="text-h3">{{ detailStore.details.getFirstName()[0] }}{{
            detailStore.details.getLastName()[0]
          }}</span>
          </v-avatar>
        </v-col>
        <v-col cols="12" lg="8" md="8" sm="12" class="pa-0 pl-md-3 pl-sm-0 pr-0">
          <v-container class="pa-2 pr-0">
            <v-row>
              <v-col cols="12" lg="12" md="12" sm="12">
                <h1> {{ detailStore.details.getFirstName() }} {{ detailStore.details.getLastName() }}</h1>
                <h3> {{ detailStore.details.getJobTitle() }}, {{ detailStore.details.getAge() }}</h3>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" lg="8" md="8" sm="12" align-self="start">
                <p class="text-no-wrap">
                  <v-icon class="mr-3" size="small" color="#BDBDBD">
                    mdi-email
                  </v-icon>
                  {{ detailStore.details.getEmail() }}
                </p>
                <p class="mt-3 text-no-wrap">
                  <v-icon class="mr-3" size="small" color="#BDBDBD">
                    mdi-crown-outline
                  </v-icon>
                  {{ detailStore.details.getPrimarySkill() }}
                </p>
              </v-col>
              <v-col cols="12" lg="4" md="4" sm="12" align-self="end" class="pr-0">
                <p class="text-no-wrap">
                  <v-icon class="mr-3 text-no-wrap" size="small" color="#BDBDBD">
                    mdi-phone
                  </v-icon>
                  {{ detailStore.details.getPhoneNumber() }}
                </p>
                <p class="mt-3 text-no-wrap">
                  <v-icon class="mr-3" size="small" color="#BDBDBD">
                    mdi-cake-variant-outline
                  </v-icon>
                  {{ detailStore.details.getBirthDate() }}
                </p>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>

      <v-row class="pt-6 pl-md-6 d-md-flex justify-md-center align-content-stretch">
        <v-col cols="12" lg="4" md="5" sm="12"
               class="d-md-flex flex-md-column align-md-center pa-sm-3 ml-0 pl-5">
          <v-card style="border: 1px solid lightgray" max-width="275" min-width="275">
            <p class="block_title pa-2">
              Skills
            </p>
            <div class="d-flex flex-wrap justify-start">
              <div v-for="skill in detailStore.details.getTechnologies()" class="pl-3 pr-1 pa-2">
                <v-chip>{{ skill }}</v-chip>
              </div>
            </div>
          </v-card>
          <v-card class="mt-3" style="border: 1px solid lightgray" max-width="275" min-width="275">
            <div class="block_title pa-2">
              Abschluss
            </div>
            <p class="pl-3 pa-2">
              {{ detailStore.details.getDegree() }}
            </p>
          </v-card>
        </v-col>
        <v-col cols="12" lg="8" md="7" sm="12" class="pl-md-2 pl-5 pr-0">
          <div class="references pt-0 ">
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
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script lang="ts">
import DropdownButton from "@/components/DropdownButton.vue";
import DeleteProfileDialog from "@/components/DeleteProfileDialog.vue";
import {useDetailStore} from "@/stores/DetailStore";
import {useRoute} from "vue-router";
import {ref} from "vue";
import router from "@/router";
import {useUserStore} from "@/stores/UserStore";

export default {
  name: "DetailComponent",
  components: {DropdownButton, DeleteProfileDialog},
  setup() {
    const detailStore = useDetailStore();
    const id = String(useRoute().params.id);
    detailStore.loadDetailsById(id);

    const locked = ref(false);
    const toDelete = ref(false);

    let dropdownFunctions = [];
    const role = window.localStorage.getItem('role');
    dropdownFunctions = [
      {name: 'Bearbeiten', method: enterEdit},
      {name: 'Löschen', method: toggleDelete},
    ];
    if (role === 'ROLE_ADMIN' && !locked.value) {
      dropdownFunctions.push({name: 'Sperren', method: lockProfile});
    }

    function enterEdit(): void {
      router.push({name: 'editView', params: {id: id}});
    }

    function toggleDelete(): void {
      toDelete.value = !toDelete.value;
    }

    function lockProfile(): void {
      const userStore = useUserStore();
      detailStore.loadDetailsById(id);
      const userId = detailStore.details.getOwnerId();
      userStore.lockUser(userId);
      locked.value = true;
    }

    function deleteProfile(): void {
      detailStore.deleteDetailsByID(id);
      router.push(`/`);
    }

    return {
      dropdownFunctions,
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


.profilePic {
  height: 200px;
}

.block_title {
  font-size: 16px;
  font-weight: bold;
  line-height: 20px;
  letter-spacing: 2px;
  font-family: "Poppins", sans-serif;
}

.block_content {
  margin-top: 12px;
  font-size: 16px;
  text-align: left;
  font-family: "Poppins", sans-serif;
}


</style>
