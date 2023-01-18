<template>

  <v-menu :close-on-content-click="false">
    <template v-slot:activator="{ props }">
      <v-btn class="pa-0" v-bind="props"
             min-width="40px" width="40px"
             height="35px" elevation="0">
        <v-icon size="large">
          mdi-cog
        </v-icon>
      </v-btn>
    </template>

    <v-list>
      <v-list-item @click="editFunction.method">
        <v-list-item-title>
          {{ editFunction.name }}
        </v-list-item-title>
      </v-list-item>
      <v-list-item v-if="lockFunction !== ''"
                   @click="lockFunction.method(); toLock = !toLock;">
        <v-list-item-title>
          {{ lockFunction.name }}
        </v-list-item-title>
      </v-list-item>
      <v-list-item @click="toDelete = !toDelete">
        <v-list-item-title>
          {{ deleteFunction.name }}
        </v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>

  <v-dialog max-width="200" v-model="toDelete">
    <v-card>
      <v-card-text>Willst du dieses Profil wirklich löschen?</v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn class="ml-1 mr-1" @click="deleteFunction.method">
          Ja
        </v-btn>
        <v-btn class="ml-1 mr-1" @click="toDelete = !toDelete">
          Nein
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-overlay v-model="toLock" absolute/>
</template>

<script lang="ts">
import {useUserStore} from "@/stores/UserStore";
import DeleteProfileDialog from "@/components/DetailComponents/DeleteProfileDialog.vue";
import {ref} from "vue";

export default {
  name: "DropdownButton",
  components: { DeleteProfileDialog },
  props: {
    editFunction: {
      required: true,
    },
    lockFunction: {
      default: '',
      required: false
    },
    deleteFunction: {
      required: true
    }
  },
  setup() {
    return {
      userStore: useUserStore(),
      toDelete: ref(false),
      toLock: ref(false)
    }
  },
  /*computed: {
    isAuthorized(): boolean {
      const role = window.localStorage.getItem('role');
      if (role === "ROLE_ADMIN" || role === "ROLE_HR") {
        return true;
      }

      const userId = window.localStorage.getItem('user_id');
      this.userStore.checkForExistingUserProfile(userId);
      if (role === "ROLE_USER") {
        if (!this.userStore.hasProfile) {
          return false;
        }
        return true;
        /!*const profileId = String(useRoute().params.id);
        this.userStore.getProfileIdFromUser(userId);
        return (String(this.userStore.profileId) === profileId);*!/
      }
      return false;
    }
  },*/
}
</script>

<style scoped>
</style>
