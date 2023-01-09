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
      <v-select v-model="selectedNamesAndRole"
                :items="users" label="Wähle Nutzer"
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
    'users',
    'selectedUsers',
  ],
  data() {
    const nextUsers = this.selectedUsers;
    const selectedNamesAndRole = [];
    nextUsers.forEach(user => {
      selectedNamesAndRole.push(`${user.getEmail()} (${user.getRole().getDisplayName()})`)
    });
    return {
      nextUsers, selectedNamesAndRole
    }
  },

  /*
  /!*Problem: selectedUsers
    *   as Prop: can't be used as v-model because readOnly
    *   as return of setup: needs role prop
    *     -> as return of setup with function: method can't be found
    *     -> as return of setup with method: method can't find return of setup
    *   as return of data: needs role prop
    *     -> as return of data with method: method can't be found
    *     -> as return of data with method: method can't find return of data
    * */
  methods: {
    async trySubmit(role, selectedNamesAndRole) {
      const userStore = useUserStore();
      for (const nameAndRole of selectedNamesAndRole) {
        const name = nameAndRole.split("(")[0].trim();
        await userStore.changeRole(role.getIdentifier(), name);
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
  max-width: 500px;
  min-width: 400px;
}

.v-select {
  min-width: 340px;
}
</style>