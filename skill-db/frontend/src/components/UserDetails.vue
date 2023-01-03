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
        <td>{{ user.getEmail() }}</td>
        <td>
          <v-chip class="roleChip" :color="user.getRole() === 'Administrator' ? 'primary' : 'green'">{{
              user.getRole()
            }}
          </v-chip>
        </td>
        <td>
          <span>
            {{createRightString(user.getRights())}}
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

<script>
export default {
  props: ['users'],
  methods: {
    createRightString(rights) {
      let rightString = "";
      rights.forEach(right => rightString += right + ", ");
      return rightString.slice(0, rightString.length - 2);
    },
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
