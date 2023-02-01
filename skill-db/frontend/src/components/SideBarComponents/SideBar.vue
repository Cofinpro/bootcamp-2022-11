<template>
  <v-app-bar elevation="0"
             order="0"
             v-if="$vuetify.display.smAndDown">
    <v-app-bar-nav-icon @click="drawer = !drawer"/>
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
      <v-list-item link v-if="profile">
        <StyledLink :to="{ name: 'userDetails', params: { id: profileId }}" icon="mdi-account-box-outline" text="Dein Profil"/>
      </v-list-item>

      <v-divider v-if="admin" class="mt-10 mb-10"/>

      <v-list-item link v-if="admin">
        <StyledLink to="/admin/users" icon="mdi-account-group" text="Nutzerübersicht"/>
      </v-list-item>
      <v-list-item link v-if="admin">
        <StyledLink to="/admin/roles" icon="mdi-shield-account" text="Rollenübersicht"/>
      </v-list-item>

    </v-list>
    <template v-slot:append>
      <logout-button @click="authStore.logout()"/>
    </template>
  </v-navigation-drawer>
</template>

<script setup lang="ts">
import {useAuthStore} from "@/stores/auth";
import StyledLink from "@/components/SideBarComponents/StyledLink.vue"
import LogoutButton from "@/components/SideBarComponents/LogoutButton.vue";
import {computed, ref} from "vue";
import type {ComputedRef} from "vue";
import {isAdmin, getProfileId, hasProfile} from "@/components/SideBarComponents/SideBarFunctions";

const props = defineProps({id: Number});

const authStore = useAuthStore();
let drawer = ref(true);
const admin: ComputedRef<boolean> = computed(() => isAdmin());
const profile: ComputedRef<boolean> = computed(() => hasProfile());
const profileId: ComputedRef<String> = computed(() => getProfileId());
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
