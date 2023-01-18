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

        <td class="d-flex justify-center pa-2 pt-3"
            v-if="isDefined(role)">
          <v-chip :color="role.getColor()">
            {{ role.getDisplayName() }}
          </v-chip>
        </td>

        <td class="description pa-2"
            v-if="isDefined(role)">
          {{ role.getDescription() }}
        </td>

        <td class="d-flex justify-center"
            v-if="isDefined(role)">
          <v-icon color="#BDBDBD"
                  @click="prepareSelectDropdown(role)">
            mdi-pencil
          </v-icon>
        </td>

      </tr>

      <v-overlay v-model="edit">
        <role-dropdown @clicked="submit"
                       :role="roleHere"
                       :selected-users="selectedUsers"
                       :all-users="allUsers"/>
      </v-overlay>

    </v-table>
  </v-container>
</template>

<script lang="ts">
import {useRoleStore} from "@/stores/RoleStore";
import {RoleModel} from "@/models/RoleModel";
import {useUserStore} from "@/stores/UserStore";
import RoleDropdown from "@/components/RoleComponents/RoleDropdown.vue";
import type {UserModel} from "@/models/UserModel";

export default {
  name: "RoleComponent",
  components: { RoleDropdown },
  data() {
    let roleStore = useRoleStore();
    roleStore.loadAllRoles();
    let roles = roleStore.allRoles;

    return {
      edit: false,
      roleHere: RoleModel,
      selectedUsers: [] as UserModel[],
      allUsers: [] as UserModel[],
      userStore: useUserStore(),
      roles
    }
  },
  methods: {
    isDefined(role: RoleModel): boolean {
      return role.getIdentifier() !== 'UNDEFINED';
    },

    async prepareSelectDropdown(role: RoleModel): Promise<void> {
      this.selectedUsers = [] as UserModel[];
      if (this.isDefined(role)) {
        await this.userStore.loadUsersByRoleId(role.getIdentifier());
        this.selectedUsers = this.userStore.users;
        this.userStore.users = [] as UserModel[];
      }
      await this.userStore.loadUsers();
      this.allUsers = this.userStore.users;

      this.edit = true;
      this.roleHere = role;
    },

    async submit(selectedUsersWithRole: string[]): Promise<void> {
      const role = this.roleHere;
      for (const selected of selectedUsersWithRole) {
        for (const user of this.allUsers) {
          if(user.getEmail() === selected.split("(")[0].trim()
              && user.getRole().getIdentifier() !== role.getIdentifier()) {
            await this.userStore.changeRole(user.getId(), role.getDisplayName());
          }
        }
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
</style>