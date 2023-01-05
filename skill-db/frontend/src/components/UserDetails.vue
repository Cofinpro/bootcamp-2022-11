<template>
  <v-container>
    <h2>Nutzerübersicht</h2>
    <h3>Übersicht aller vorhandenen Nutzer</h3>
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
      <tr v-for="user in users"
          :key="user.getEmail()"
          :class="{ locked: user.locked, notLocked: !user.locked}">
        <td>
          {{ user.getEmail() }}
        </td>

        <td class="d-flex justify-center ma-1">
          <ChipWithTooltip :tooltip="user.getRole().getDescription()"
                           :content="user.getRole().getDisplayName()"
                           :color="roleColor(user.getRole().getIdentifier())"/>
        </td>
        <td>
          <v-icon @click="user.locked = !user.locked"
                  :class="{ locked: user.locked, notLocked: !user.locked}">
            {{ user.locked ? 'mdi-lock' : 'mdi-lock-open' }}
          </v-icon>
        </td>
      </tr>
    </v-table>
  </v-container>
</template>

<script lang="ts">
import ChipWithTooltip from "@/components/ChipWithTooltip.vue";
export default {
  name: "UserDetails",
  components: { ChipWithTooltip },
  props: ['users'],
  data() {
    return {
      alert: false
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
