<template>
  <v-container>
    <h2>Nutzerübersicht</h2>
    <h3>Übersicht aller vorhandenen Nutzer</h3>
    <v-table>
      <thead>
      <tr>
        <th>
          Email
        </th>
        <th class="text-left">
          Rolle
        </th>
        <th class="text-left">
          Rechte
        </th>
        <th class="text-left">
          Aktionen
        </th>
      </tr>
      </thead>
      <tr v-for="user in users"
          :key="user.getEmail()">
        <td>
          {{ user.getEmail() }}
        </td>
        <td>
          <v-chip class="roleChip" :color="roleColor(user.getRole().getIdentifier())">
            {{ user.getRole().getDisplayName() }}
          </v-chip>
        </td>
        <td>
          <span>
            {{ user.getRole().getDescription() }}
          </span>
        </td>
        <td>
          <v-icon :class="{ locked: user.locked, notLocked: !user.locked}">
            mdi-lock
          </v-icon>
        </td>
      </tr>
    </v-table>
  </v-container>
</template>

<script lang="ts">
export default {
  props: ['users'],
  methods: {
    roleColor(roleShortName: String): String {
      if(roleShortName === 'ADMIN') {
        return '--primary-orange';
      } else if(roleShortName === 'HR') {
        return '--primary-blue';
      } else if(roleShortName === 'USER'){
        return 'green';
      } else {
        return 'red';
      }
    }
  }
}
</script>

<style scoped>

.roleChip {
  margin: 3px;
}

.locked {
  color: lightgray;
}

.notLocked {
  cursor: pointer;
  color: gray;
}
</style>
