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
          Aktionen
        </th>
      </tr>
      </thead>

      <tr v-for="role in roles"
          :key="role.getIdentifier()">
        <td class="d-flex justify-center ma-1" v-if="role.getIdentifier() !== 'UNDEFINED'">
          <v-chip :color="roleColor(role.getIdentifier())">
            {{ role.getDisplayName() }}
          </v-chip>
        </td>
        <td class="description" v-if="role.getIdentifier() !== 'UNDEFINED'">
          {{ role.getDescription() }}
        </td>
        <td class="d-flex justify-center" v-if="role.getIdentifier() !== 'UNDEFINED'">
          <v-icon color="#BDBDBD" @click="prepareSelect(role)">
            mdi-pencil
          </v-icon>

          <v-overlay v-model="edit">
            <v-card elevation="2">
              <div class="d-flex justify-space-between">
              <v-card-title>
                {{ roleHere.getIdentifier() }}
              </v-card-title>
              <v-card-actions>
                <v-btn class="mr-2 mt-2" @click="submit(roleHere)"
                       elevation="0" size="small">
                  Bestätigen
                </v-btn>
              </v-card-actions>
              </div>

              <v-card-item class="d-flex flex-column justify-space-between">
                <v-select v-if="edit" v-model="selectedUsers"
                          :items="users" label="Wähle Nutzer"
                          multiple class="ml-5 mr-5" hide-details>
                  <template v-slot:selection="{ item, index }">
                    <v-chip v-if="index <= 0">
                      <span>{{ item.title }}</span>
                    </v-chip>
                    <span v-if="index === 1"
                          class="grey--text text-caption">
                    (+{{ selectedUsers.length - 1 }} others)
                  </span>
                  </template>
                </v-select>
              </v-card-item>
            </v-card>
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

export default {
  name: "RoleDetails",
  props: ['roles', 'users'],
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
    async prepareSelect(role) {
      this.selectedUsers = [];
      if (role.getIdentifier() !== 'UNDEFINED') {
        await this.roleStore.loadUsersById(role.getIdentifier());
        this.selectedUsers = this.roleStore.user;
      }
      this.roleHere = role;
      this.edit = true;
    },
    async submit(role) {
      for (const user of this.selectedUsers) {
        await this.userStore.changeRole(role.getIdentifier(), user);
        console.log(`User: ${user}, Role: ${role.getIdentifier()}`);
      }
      this.edit = false;
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