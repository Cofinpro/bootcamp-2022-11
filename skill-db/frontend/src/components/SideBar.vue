<template>
  <v-app-bar elevation="0"
             order="0"
             v-if="$vuetify.display.smAndDown">
    <v-app-bar-nav-icon @click="toggleDrawer()"/>
  </v-app-bar>

  <v-navigation-drawer elevation="0"
                       v-model="drawer" order="1"
                       :scrim="false"
                       :permanent="$vuetify.display.mdAndUp">

    <v-list nav dense>

      <v-list-item class="logo d-flex justify-center">
        <img src="@/assets/images/Skill_DB_Logo.svg" width="180" alt="Cofinpro logo">
      </v-list-item>

      <v-list-item link>
        <StyledLink to="/" icon="mdi-account-box-multiple-outline" text="Profilübersicht"/>
      </v-list-item>
      <v-list-item link v-if="hasProfile">
        <StyledLink :to="{ name: 'userDetails', params: { id: getProfileId }}" icon="mdi-account-box-outline" text="Dein Profil"/>
      </v-list-item>

      <v-divider v-if="isAdmin" class="mt-10 mb-10"/>

      <v-list-item link v-if="isAdmin">
        <StyledLink to="/admin/users" icon="mdi-account-group" text="Nutzerübersicht"/>
      </v-list-item>
      <v-list-item link v-if="isAdmin">
        <StyledLink to="/admin/roles" icon="mdi-shield-account" text="Rollenübersicht"/>
      </v-list-item>

    </v-list>
    <template v-slot:append>
      <logout-button @click="logout"/>
    </template>
  </v-navigation-drawer>
</template>

<script lang="ts">
import {useAuthStore} from "@/stores/auth";
import {useUserStore} from "@/stores/UserStore";
import StyledLink from "@/components/SideBarComponents/StyledLink.vue"
import LogoutButton from "@/components/SideBarComponents/LogoutButton.vue";

export default {
  name: "SideBar",
  components: {LogoutButton, StyledLink },
  props: {
    id: Number,
  },
  computed: {
    isAdmin() {
      return window.localStorage.getItem('role') === 'ROLE_ADMIN';
    },
    hasProfile() {
      const userStore = useUserStore();
      const userId = window.localStorage.getItem('user_id');
      userStore.checkForExistingUserProfile(userId);
      return userStore.hasProfile;
    },
    getProfileId() {
      this.hasProfile; //to force reload
      const userStore = useUserStore();
      const userId = window.localStorage.getItem('user_id');
      userStore.getProfileIdFromUser(userId);
      return userStore.profileId;
    }
  },
  data() {
    return {
      store: useAuthStore(),
      drawer: null,
    }
  },
  methods: {
    logout() {
      this.store.logout();
    },
    toggleDrawer() {
      this.drawer = !this.drawer;
    },
  }
}
</script>

<style scoped>

.logo {
  margin-top: 40%;
  margin-bottom: 30px;
}

.v-list-item {
  display: flex;
  justify-content: start;
}
</style>
