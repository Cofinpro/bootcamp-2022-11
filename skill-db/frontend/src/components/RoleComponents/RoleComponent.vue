<template>
  <v-container>
    <h2>Rollenübersicht</h2>
    <h3>Übersicht über alle vorhandenen Rollen</h3>
    <v-table>

      <thead>
      <tr>
        <th class="text-left">
          Name
        </th>
        <th class="text-left">
          Beschreibung
        </th>
        <th class="text-center">
          Rolle vergeben
        </th>
      </tr>
      </thead>

      <tr v-for="role in state.allRoles"
          :key="role.getIdentifier()">

        <td class="d-flex justify-center pa-2 pt-3">
          <v-chip :color="role.getColor()">
            {{ role.getDisplayName() }}
          </v-chip>
        </td>

        <td class="description pa-2">
          {{ role.getDescription() }}
        </td>

        <td class="d-flex justify-center">
          <v-icon color="#BDBDBD"
                  @click="state.prepareSelectDropdown(role)">
            mdi-pencil
          </v-icon>
        </td>

      </tr>

      <v-overlay v-model="state.edit">
        <role-dropdown @clicked="state.submit()"
                       :state="state"/>
      </v-overlay>

    </v-table>
  </v-container>
</template>

<script setup lang="ts">
import RoleDropdown from "@/components/RoleComponents/RoleDropdown.vue";
import {ref} from "vue";
import {RoleComponentState} from "@/components/RoleComponents/RoleComponentState";

const name = "RoleComponent";
const state = ref(new RoleComponentState());
state.value.loadAllRoles();
</script>

<style scoped>

.description {
  font-size: small;
}
</style>