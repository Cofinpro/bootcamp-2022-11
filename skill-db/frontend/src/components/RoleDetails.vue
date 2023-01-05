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
        <td class="d-flex justify-center ma-1">
          <v-chip :color="roleColor(role.getIdentifier())">
            {{ role.getDisplayName() }}
          </v-chip>
        </td>
        <td class="description">
          {{ role.getDescription() }}
        </td>
        <td class="d-flex justify-center">
          <v-icon color="#BDBDBD" @click="edit = true">
            mdi-pencil
          </v-icon>

          <v-overlay v-model="edit" absolute>
            <v-card class="d-flex justify-space-between" elevation="2">
              <!--            <v-autocomplete v-if="edit" v-model="selectedUsers" label="Wähle Anwender"
                                          :items="getUserNames()"
                                          multiple auto-select-first/>-->
              <v-select v-if="edit" v-model="selectedUsers"
                        :items="users" label="Wähle Anwender"
                        multiple class="ml-5 mt-5">
                <template v-slot:selection="{ item, index }">
                  <v-chip v-if="index <= 1">
                    <span>{{ item.title }}</span>
                  </v-chip>
                  <span v-if="index === 2"
                        class="grey--text text-caption">
                    (+{{ selectedUsers.length - 2 }} others)
                  </span>
                </template>
              </v-select>

              <v-card-actions>
                <v-icon class="mr-2 ml-2" @click="edit = false">
                  mdi-close-circle-outline
                </v-icon>
              </v-card-actions>
            </v-card>
          </v-overlay>

        </td>
      </tr>
    </v-table>
  </v-container>
</template>

<script lang="ts">
export default {
  name: "RoleDetails",
  props: ['roles', 'users'],
  data() {
    return {
      edit: false,
      selectedUsers: [] as String[],
    }
  },
  methods: {
    roleColor(roleShortName: String): String {
      if (roleShortName === 'ADMIN') {
        return '#ec7b1a';
      } else if (roleShortName === 'HR') {
        return '#9bc3ee';
      } else if (roleShortName === 'USER') {
        return '#3a3a3a';
      } else {
        return 'red';
      }
    }
  }
}
</script>

<style scoped>

.description {
  font-size: small;
}

.v-card {
  margin-top: 20vh;
  margin-left: 20vw;
  max-width: 600px;
  min-width: 400px;
}
</style>