<template>
  <v-menu :close-on-content-click="false">
    <template v-slot:activator="{ props }">
      <v-btn class="pa-0" v-bind="props"
             min-width="40px" width="40px" height="35px"
             elevation="0">
        <v-icon size="large">
          mdi-cog
        </v-icon>
      </v-btn>
    </template>

    <v-list>
      <v-list-item v-for="func in functions" link
                   :key="func.name" @click="func.method">
        <v-list-item-title>
          {{ func.name }}
        </v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>
</template>

<script lang="ts">

import {useUserStore} from "@/stores/UserStore";


export default {
  name: "DropdownButton",
  props: {
    functions: {
      type: Array,
      required: true,
    }
  },
  setup() {
    const userStore = useUserStore();
    return {
      userStore,
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
