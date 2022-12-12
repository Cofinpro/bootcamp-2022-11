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
        <button>Logout</button>
      </div>
    </div>
  </v-navigation-drawer>
</template>

<script type="text/babel">
export default {
  props: ['id'],
}

</script>

<style lang="css">
</style>
