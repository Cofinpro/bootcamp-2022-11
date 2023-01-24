<template>
  <v-container :v-if="!userStore.loading">
    <h2>Nutzerübersicht</h2>
    <h3>Übersicht über alle vorhandenen Nutzer</h3>
    <v-table>

      <thead>
      <tr>
        <th class="text-left">
          Email
        </th>
        <th class="text-left">
          Rolle
        </th>
        <th class="text-left">
          Sperrstatus
        </th>
      </tr>
      </thead>

      <tr v-for="user in userStore.users"
          :key="user.getEmail()"
          :class="{ locked: user.getLocked()}">

        <td>
          {{ user.getEmail() }}
        </td>

        <td class="d-flex justify-left align-center ma-1">
          <ChipWithInfotext :tooltip="user.getRole().getDescription()"
                            :content="user.getRole().getDisplayName()"
                            :color="user.getRole().getColor()"/>
          <AlertWithTooltip :user="user"
                            operation-type="changeRole"
                            :operations="userStore.roleChangeOperations"/>
        </td>

        <td>
          <div class="d-flex justify-start">
            <v-icon @click="toggleLock(user)"
                    v-if="user.getLocked()">
              mdi-lock
            </v-icon>
            <v-icon @click="toggleLock(user)"
                    v-else>
              mdi-lock-open
            </v-icon>
            <AlertWithTooltip :user="user"
                              operation-type="lockUser"
                              :operations="userStore.lockUserOperations"/>
          </div>
        </td>

      </tr>
    </v-table>
  </v-container>
</template>

<script lang="ts">
import ChipWithInfotext from "@/components/ChipWithInfotext.vue";
import {useUserStore} from "@/stores/UserStore";
import type {UserModel} from "@/models/UserModel";
import {useErrorStore} from "@/stores/ErrorStore";
import AlertWithTooltip from "@/components/UserComponents/AlertWithTooltip.vue";

export default {
  name: "UserComponent",
  components: {AlertWithTooltip, ChipWithInfotext},
  setup() {
    const userStore = useUserStore();
    userStore.loadUsers();
    userStore.loadPendingRoleChanges();
    userStore.loadPendingLockUsers();

    const errorStore = useErrorStore();

    async function toggleLock(user: UserModel): Promise<void> {
      await userStore.lockUser(user.getId());
      if (!errorStore.hasError) {
        user.setLocked(!user.getLocked());
      }
      await userStore.loadPendingLockUsers();
    }

    return {
      userStore, toggleLock
    }
  }
}
</script>

<style scoped>

.v-icon {
  margin-left: 20%;
}

.locked {
  color: lightgray;
}
</style>
