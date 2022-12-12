<template>
  <v-navigation-drawer
      color="#F1F1F1"
      elevation="4"
      permanent
      expand-on-hover
      app
  >
    <v-divider/>
    <div class="wrapper">
      <v-list nav dense>
        <v-list-item link href="#">
          <img src="@/assets/pictures/Logo.png" alt="A logo">
        </v-list-item>
        <v-list-item link href="#">
          <RouterLink to="/">Profilübersicht</RouterLink>
        </v-list-item>
        <v-list-item>
          <RouterLink to="/test">Detailtest</RouterLink>
        </v-list-item>
<!--        <v-list-item>
          <RouterLink :to="{ name: 'userDetail', params: { id: id }}">Dein Profil</RouterLink>
        </v-list-item>-->
      </v-list>
      <div>
        <v-btn @click="logout">Logout</v-btn>
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
