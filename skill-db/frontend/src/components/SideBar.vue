<template>
  <v-navigation-drawer
      elevation="0"
      permanent
      expand-on-hover
      app
  >
    <div class="wrapper">
      <v-list nav dense>
        <v-list-item id="logo">
          <img src="@/assets/pictures/Logo.png" alt="Cofinpro logo">
        </v-list-item>
        <v-list-item link>
          <v-icon size="small">mdi-account-box-multiple-outline</v-icon>
          <RouterLink to="/">Profilübersicht</RouterLink>
        </v-list-item>
        <v-list-item link>
          <v-icon size="small">mdi-account-box-outline</v-icon>
          <RouterLink to="/test">Detailtest</RouterLink>
        </v-list-item>
<!--        <v-list-item>
          <RouterLink :to="{ name: 'userDetail', params: { id: id }}">Dein Profil</RouterLink>
        </v-list-item>-->
      </v-list>
          <div class="logout">
            <v-btn @click="logout">
              Ausloggen
              <v-icon color="--primary-orange">mdi-exit-to-app</v-icon>
            </v-btn>
          </div>
    </div>
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
