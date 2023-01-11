<template>
  <v-card elevation="2">
    <div class="d-flex justify-space-between">
      <v-card-title>
        {{ roleHere.getDisplayName() }}
      </v-card-title>
      <v-card-actions>
        <v-btn class="mr-2 mt-2" @click="trySubmit(roleHere, selectedNamesAndRole)"
               elevation="0" size="small">
          Bestätigen
        </v-btn>
      </v-card-actions>
    </div>

    <v-card-item class="d-flex flex-column justify-space-between">
      <v-select v-model="selectedNamesAndRole" item-title="email"
                :items="attachRole(users)" label="Wähle Nutzer"
                multiple class="ml-5 mr-5" hide-details>
        <template v-slot:selection="{ item, index }">
          <v-chip v-if="index <= 0">
            <span>{{ item.title }}</span>
          </v-chip>
          <span v-if="index === 1"
                class="grey--text text-caption">
                    (+{{ selectedNamesAndRole.length - 1 }} mehr)
                  </span>
        </template>
      </v-select>
    </v-card-item>
  </v-card>
</template>

<script lang="js"> /*TODO should be TypeScript*/
import {useUserStore} from "@/stores/UserStore";

export default {
  name: "RoleDropdown",
  props: [
    'roleHere',
    'selectedUsers',
  ],
  data() {
    const userStore = useUserStore();
    userStore.loadUsers();
    const users = userStore.users

    const nextUsers = this.selectedUsers;
    const selectedNamesAndRole = [];
    nextUsers.forEach(user => {
      selectedNamesAndRole.push(`${user.getEmail()} (${user.getRole().getDisplayName()})`)
    });
    function attachRole(users) {
      let namesAndRoles = [];
      users.forEach(user => {
        namesAndRoles.push(`${user.getEmail()} (${user.getRole().getDisplayName()})`)
      });
      return namesAndRoles;
    }
    return {
      users, nextUsers, selectedNamesAndRole, attachRole
    }
  },
  methods: {
    async trySubmit(role, nextUsers) {
      const userStore = useUserStore();
      for (const selected of nextUsers) {
        this.users.forEach(user => {
          if(user.getEmail() === selected.split("(")[0].trim()) {
            userStore.changeRole(role.getIdentifier(), user.getId());
          }
        })

      }
      this.$emit('clicked');
    },


  }
}
</script>

<style scoped>

.v-card {
  margin-top: 20vh;
  margin-left: 20vw;
  width: 500px;
}

.v-select {
  min-width: 400px;
}
</style>