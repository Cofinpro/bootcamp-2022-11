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
        <th class="text-center">
          Rolle
        </th>
        <th class="text-center">
          Sperrstatus
        </th>
      </tr>
      </thead>
      <tr v-for="user in userStore.users"
          :key="user.getEmail()"
          :class="{ locked: user.getLocked(), notLocked: !user.getLocked()}">
        <td>
          {{ user.getEmail() }}
        </td>

        <td class="d-flex justify-center ma-1">
          <ChipWithInfotext :tooltip="user.getRole().getDescription()"
                           :content="user.getRole().getDisplayName()"
                           :color="roleColor(user.getRole().getIdentifier())"/>
        </td>
        <td>
          <v-icon @click="toggleLock(user)"
                  :class="{ locked: user.getLocked(), notLocked: !user.getLocked()}">
            {{ user.getLocked() ? 'mdi-lock' : 'mdi-lock-open' }}
          </v-icon>
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
export default {
  name: "UserDetails",
  components: { ChipWithInfotext },
  setup() {
    const userStore = useUserStore();
    userStore.loadUsers();
    return {
      userStore
    }
  },
  methods: {
    roleColor(roleShortName: String): String {
      if(roleShortName === 'ADMIN') {
        return '#ec7b1a';
      } else if(roleShortName === 'HR') {
        return '#9bc3ee';
      } else if(roleShortName === 'USER'){
        return '#3a3a3a';
      } else {
        return 'red';
      }
    },
    async toggleLock(user: UserModel): Promise<void> {
      const userStore = useUserStore();
      const errorStore = useErrorStore();
      await userStore.lockUser(user.getId());
      if (!errorStore.hasError) {
        user.setLocked(!user.getLocked());
      }
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
