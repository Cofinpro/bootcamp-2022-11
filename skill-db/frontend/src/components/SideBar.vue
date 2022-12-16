<template>
  <v-navigation-drawer
      elevation="0"
      permanent
      expand-on-hover
      app
  >
    <!-- <div class="wrapper"> -->
    <v-list nav dense>
      <v-list-item id="logo">
        <img src="@/assets/images/Logo.png" alt="Cofinpro logo">
      </v-list-item>
      <v-list-item link>
        <v-icon size="small" color="#BDBDBD" class="ml-8 mr-3">mdi-account-box-multiple-outline</v-icon>
        <RouterLink to="/">Profilübersicht</RouterLink>
      </v-list-item>
      <v-list-item link>
        <v-icon size="small" color="#BDBDBD" class="ml-8 mr-3">mdi-account-box-outline</v-icon>
        <RouterLink to="/test">Detailtest</RouterLink>
      </v-list-item>
      <!--        <v-list-item link>
                <RouterLink :to="{ name: 'userDetail', params: { id: id }}">Dein Profil</RouterLink>
              </v-list-item>-->
    </v-list>

    <template v-slot:append>
      <div class="ma-10 pa-5">
        <v-btn elevation="0">
          LOGOUT
          <v-icon class="ml-3" @click="logout">mdi-exit-to-app</v-icon>
        </v-btn>
      </div>
    </template>
  </v-navigation-drawer>
</template>

<script type="text/babel">
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
