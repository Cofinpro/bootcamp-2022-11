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
        <th class="text-center">
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
                            :color="roleColor(user.getRole().getIdentifier())"/>
          <AlertWithTooltip :user="user" operation-type="changeRole" :operations="userStore.roleChangeOperations"/>

        </td>

        <td>
          <div class="d-flex justify-start">
            <v-icon @click="toggleLock(user)"
                    :class="{ locked: user.getLocked()}">
              {{ user.getLocked() ? 'mdi-lock' : 'mdi-lock-open' }}
            </v-icon>
            <AlertWithTooltip :user="user" operation-type="lockUser" :operations="userStore.lockUserOperations"/>
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
  data() {
    let errorStore = useErrorStore();
    let userStore = useUserStore();
    userStore.loadUsers();
    userStore.loadPendingRoleChanges();
    userStore.loadPendingLockUsers();
    return {
      userStore, errorStore
    }
  },
  methods: {
    roleColor(roleShortName: string): string {
      if (roleShortName === 'ADMIN') {
        return '#ec7b1a';
      } else if (roleShortName === 'HR') {
        return '#9bc3ee';
      } else if (roleShortName === 'USER') {
        return '#3a3a3a';
      } else {
        return 'black';
      }
    },

    async toggleLock(user: UserModel): Promise<void> {
      await this.userStore.lockUser(user.getId());
      if (!this.errorStore.hasError) {
        user.setLocked(!user.getLocked());
      }
      await this.userStore.loadPendingLockUsers();
      //TODO: Lock doesn't close, but is grey
    }
  }
}
</script>

<style scoped>

.v-icon {
  margin-left: 40%;
}

.locked {
  color: lightgray;
}
</style>
