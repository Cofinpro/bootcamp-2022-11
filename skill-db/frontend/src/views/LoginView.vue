<template>

  <v-container fluid class="pt-0 px-0 pb-0 mb-0">
    <v-row>

      <v-col cols="12" class="d-lg-none">
        <v-img
            src="https://images.unsplash.com/photo-1495360010541-f48722b34f7d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=436&q=80"
            alt="Comic Picture"
            width="100vw"
            max-height="250"
            contain
        ></v-img>
      </v-col>

      <v-col cols="12" lg="6" class="text-center mt-10">

        <v-row>
          <v-col cols="12">
            <img src="../assets/images/Logo.png" alt="Logo">
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

              <v-form v-model="valid">
                <v-text-field
                    label="Username"
                    v-model="userRequestLogin.username"
                    variant="outlined"
                    :rules="[rules.required]"
                >
                </v-text-field>
                <v-text-field
                    v-model="userRequestLogin.password"
                    :append-icon="visible ? 'mdi-eye' : 'mdi-eye-off'"
                    :type="visible ? 'text' : 'password'"
                    variant="outlined"
                    label="Passwort"
                    @click:append="visible = !visible"
                    :rules="[rules.required, rules.min]"
                    counter
                ></v-text-field>
                <v-btn @click="login()" class="mb-10 mt-10" block
                       :disabled="!valid">
                  Login
                </v-btn>
              </v-form>
            </v-card>
          </v-col>
        </v-row>


      </v-col>

      <v-col cols="6" class="d-none d-lg-block">
        <v-img
            cover
            max-height="100vh"
            src="https://images.unsplash.com/photo-1495360010541-f48722b34f7d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=436&q=80"
        ></v-img>
      </v-col>

    </v-row>
  </v-container>

</template>

<script>
import {useAuthStore} from "@/stores/auth";
import {LoginRequest} from "@/models/LoginRequest";

export default {
  name: "LoginView",
  setup() {
    const store = useAuthStore();
    return {
      store
    }
  },
  data() {
    return {
      userRequestLogin: new LoginRequest(),
      visible: false,
      valid: false,
      rules: {
        required: value => !!value || 'Erforderlich.',
        min: v => v.length >= 8 || 'Min. 8 Buchstaben',
      },
    }
  },
  methods: {
    login() {
      this.store.login(this.userRequestLogin);
    },
  }
}
</script>

<style scoped>

</style>
