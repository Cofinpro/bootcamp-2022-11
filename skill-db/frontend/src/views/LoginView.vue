<template>

  <v-container fluid class="pt-0 px-0 pb-0 mb-0">
    <v-row>

      <v-col cols="12" class="d-lg-none">
        <ComicOfTheDay :mobile="true"/>
      </v-col>

      <v-col cols="12" lg="6" class="text-center mt-10">

        <v-row>
          <v-col cols="12">
            <img src="../assets/images/Skill_DB_Logo.svg" width="220" alt="Logo">
          </v-col>
        </v-row>

        <v-row>
          <v-col cols="12">
            <v-card class="px-10">

              <v-card-title class="mt-10">
                Login
              </v-card-title>

              <v-card-subtitle class="mb-10">
                Willkommen zurück!
              </v-card-subtitle>

              <v-form v-model="valid" >
                <v-text-field
                    label="Username"
                    autocomplete="username"
                    v-model="userRequestLogin.username"
                    variant="outlined"
                    :rules="[rules.required]"/>
                <v-text-field class="align-content-sp"
                              v-model="userRequestLogin.password"
                              :append-inner-icon="visible ? 'mdi-eye' : 'mdi-eye-off'"
                              :type="visible ? 'text' : 'password'"
                              variant="outlined"
                              label="Passwort"
                              autocomplete="current-password"
                              @click:append-inner="visible = !visible"
                              :rules="[rules.required, rules.min]"
                              counter
                              @keydown.enter="login"/>
                <v-btn @click="login" class="mb-10 mt-10" block
                       :disabled="!valid" elevation="0">
                  Login
                </v-btn>
              </v-form>
            </v-card>
          </v-col>
        </v-row>
      </v-col>

      <v-col cols="6" class="d-none d-lg-block">
        <ComicOfTheDay :mobile="false"/>
      </v-col>

    </v-row>
  </v-container>

</template>

<script setup lang="ts">
import {useAuthStore} from "@/stores/auth";
import {LoginRequest} from "@/models/LoginRequest";
import ComicOfTheDay from "@/components/LoginComponents/ComicOfTheDay.vue";
import {useComicStore} from "@/components/LoginComponents/ComicStore";
import {ref} from "vue";

const authStore = useAuthStore();
const comicStore = useComicStore();
comicStore.loadComicOfTheDay();

const visible = ref(false);
const valid = ref(false);
const rules = {
  required: (value:string) => !!value || 'Erforderlich.',
  min: (v:string) => v.length >= 8 || 'Min. 8 Buchstaben',
};
const userRequestLogin = ref(new LoginRequest());

function login() {
  authStore.login(userRequestLogin.value);
}
</script>
