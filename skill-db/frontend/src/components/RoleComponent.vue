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
import {useSubmit, isDefined} from "@/components/RoleComponents/RoleDropdownFunctions";
import {ref} from "vue";

export default {
  name: "RoleComponent",
  components: { RoleDropdown },
  setup() {
    const roleStore = useRoleStore();
    roleStore.loadAllRoles();
    const roles = roleStore.allRoles;
    const userStore = useUserStore();
    const edit = ref(false);
    const selectedUsers = ref([] as UserModel[]);
    const allUsers = ref([] as UserModel[]);
    const roleHere = ref(new RoleModel());

    async function prepareSelectDropdown(role: RoleModel): Promise<void> {
      selectedUsers.value = [] as UserModel[];
      if (isDefined(role)) {
        await userStore.loadUsersByRoleId(role.getIdentifier());
        selectedUsers.value = userStore.users;
        userStore.users = [] as UserModel[];
      }
      await userStore.loadUsers();
      allUsers.value = userStore.users;

      edit.value = true;
      roleHere.value = role;
    }

    async function submit(args: any): Promise<void> {
      await useSubmit(args);
      edit.value = false;
    }

    return {
      edit, roleHere, roles,
      selectedUsers, allUsers, userStore,
      prepareSelectDropdown, submit
    }
  }
}
</script>

<style scoped>

.description {
  font-size: small;
}
</style>