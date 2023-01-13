<template>
  <v-app-bar elevation="0"
             order="0"
             v-if="$vuetify.display.sm">
    <v-app-bar-nav-icon @click="toggleDrawer()" />
  </v-app-bar>
  <v-navigation-drawer elevation="0"
                       v-model="drawer" order="1"
                       :scrim="false"
                       :permanent="$vuetify.display.smAndUp">
    <v-list nav dense>
      <v-list-item class="logo d-flex justify-center">
        <img src="@/assets/images/Skill_DB_Logo.svg" width="180" alt="Cofinpro logo">
      </v-list-item>

      <RouterLink to="/">
        <v-list-item link>
          <v-icon size="small" color="#BDBDBD" class="ml-8 mr-3">
            mdi-account-box-multiple-outline
          </v-icon>
          Profilübersicht
        </v-list-item>
      </RouterLink>
      <!--TODO get id of user-->
<!--      <RouterLink v-if="hasProfile" :to="{ name: 'userDetail', params: { id: getProfileId }}">
        <v-list-item link>
          <v-icon size="small" color="#BDBDBD" class="ml-8 mr-3">
            mdi-account-box-outline
          </v-icon>
          Dein Profil
        </v-list-item>
      </RouterLink>-->
      <v-divider class="divider mt-10 mb-10" v-if="isAdmin"/>

      <RouterLink to="/admin/users" v-if="isAdmin">
        <v-list-item link>
          <v-icon size="small" color="#BDBDBD" class="ml-8 mr-3">
            mdi-account-group
          </v-icon>
          Nutzerübersicht
        </v-list-item>
      </RouterLink>
      <RouterLink to="/admin/roles" v-if="isAdmin">
        <v-list-item link>
          <v-icon size="small" color="#BDBDBD" class="ml-8 mr-3">
            mdi-shield-account
          </v-icon>
          Rollenübersicht
        </v-list-item>
      </RouterLink>
    </v-list>

    <template v-slot:append>
      <div class="ma-10 pa-5">
        <v-btn elevation="0" @click="logout">
          LOGOUT
          <v-icon class="ml-3">
            mdi-exit-to-app
          </v-icon>
        </v-btn>
      </div>
    </template>
  </v-navigation-drawer>
</template>

<script> /*TODO should be TypeScript*/
import {useAuthStore} from "@/stores/auth";

export default {
  name: "SideBar",
  components: {},

  props: {
    id: Number,
  },
  computed: {
    isAdmin() {
      return window.localStorage.getItem('role') === 'ROLE_ADMIN';
    },
  },
  data() {
    const store = useAuthStore();

    return {
      store,
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

<style>
</style>
