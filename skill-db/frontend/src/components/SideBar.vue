<template>
  <v-navigation-drawer
      elevation="0"
      permanent
      expand-on-hover
      app
  >
    <!-- <div class="wrapper"> -->
    <v-list nav dense>
      <v-list-item class="logo d-flex justify-center">
        <img src="@/assets/images/Skill_DB_Logo.svg" width="220" alt="Cofinpro logo">
      </v-list-item>
      <RouterLink to="/">
        <v-list-item link>
          <v-icon size="small" color="#BDBDBD" class="ml-8 mr-3">mdi-account-box-multiple-outline</v-icon>
          Profilübersicht
        </v-list-item>
      </RouterLink>
      <RouterLink to="/test">
        <v-list-item link>
          <v-icon size="small" color="#BDBDBD" class="ml-8 mr-3">mdi-account-box-outline</v-icon>
          Detailtest
        </v-list-item>
      </RouterLink>
      <!-- <RouterLink :to="{ name: 'userDetail', params: { id: id }}">
             <v-list-item link>
                Dein Profil
              </v-list-item>
           </RouterLink>-->
    </v-list>

    <template v-slot:append>
      <div class="ma-10 pa-5">
        <v-btn elevation="0" @click="logout">
          LOGOUT
          <v-icon class="ml-3">mdi-exit-to-app</v-icon>
        </v-btn>
      </div>
    </template>
  </v-navigation-drawer>
</template>

<script>
import {useAuthStore} from "@/stores/auth";

export default {
  props: ['id'],
  setup() {
    const store = useAuthStore();
    return {
      store
    }
  },
  methods: {
    logout() {
      this.store.logout();
    }
  }
}

</script>

<style lang="css">
</style>
