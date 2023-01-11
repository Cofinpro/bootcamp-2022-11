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

      <tr v-for="role in roles"
          :key="role.getIdentifier()">
        <td class="d-flex justify-center pa-2 pt-3" v-if="isDefined(role)">
          <v-chip :color="roleColor(role.getIdentifier())">
            {{ role.getDisplayName() }}
          </v-chip>
        </td>
        <td class="description pa-2" v-if="isDefined(role)">
          {{ role.getDescription() }}
        </td>
        <td class="d-flex justify-center" v-if="isDefined(role)">
          <v-icon color="#BDBDBD" @click="prepareSelect(role)">
            mdi-pencil
          </v-icon>

          <v-overlay v-model="edit">
            <role-dropdown @clicked="edit = false"
                           :role-here="roleHere" :selected-users="selectedUsers"/>
          </v-overlay>

        </td>
      </tr>
    </v-table>
  </v-container>
</template>

<script> /*TODO should be TypeScript*/
import {useRoleStore} from "@/stores/RoleStore";
import {RoleModel} from "@/models/RoleModel.ts";
import {useUserStore} from "@/stores/UserStore";
import RoleDropdown from "@/components/RoleDropdown.vue";

export default {
  name: "RoleDetails",
  components: { RoleDropdown },
  props: ['roles'],
  data() {
    return {
      edit: false,
      roleHere: new RoleModel(),
      selectedUsers: [],
      roleStore: useRoleStore(),
      userStore: useUserStore(),
    }
  },
  methods: {
    roleColor(roleShortName) {
      if (roleShortName === 'ADMIN') {
        return '#ec7b1a';
      } else if (roleShortName === 'HR') {
        return '#9bc3ee';
      } else if (roleShortName === 'USER') {
        return '#3a3a3a';
      } else {
        return 'red';
      }
    },
    isDefined(role) {
      return role.getIdentifier() !== 'UNDEFINED';
    },
    async prepareSelect(role) {
      this.selectedUsers = [];
      if (role.getIdentifier() !== 'UNDEFINED') {
        await this.userStore.loadUsersByRoleId(role.getIdentifier());
        this.selectedUsers = this.userStore.users;
        this.userStore.users = [];
      }
      this.roleHere = role;
      this.edit = true;
    },
  },
}
</script>

<style scoped>

.description {
  font-size: small;
}

.v-card {
  margin-top: 20vh;
  margin-left: 20vw;
  max-width: 500px;
  min-width: 400px;
}

.v-select {
  min-width: 340px;
}
</style>