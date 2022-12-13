<template>
  <v-container>
    <h2>Nutzerübersicht<v-btn class="float-right">+</v-btn></h2>
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
        <td>{{user.getEmail()}}</td>
        <td>{{user.getRole()}}</td>
        <td>
          <span v-for="right in user.getRights()">
            {{right + " "}}
          </span>
        </td>
        <td><v-icon :class="{ locked: user.locked, notlocked: !user.locked}">
          mdi-lock
        </v-icon></td>
      </tr>
    </v-table>
  </v-container>
</template>

<script>
export default {
  props: ['users'],
}
</script>

<style scoped>
.locked{
  color: lightgray;
}
.notlocked{
  cursor: pointer;
  color: gray;
}
</style>
