<template>
  <RoleDetails :roles="roles" :users="getNames(allUsers)"/>
</template>

<script lang="ts">
import RoleDetails from "@/components/RoleDetails.vue";
import {useRoleStore} from "@/stores/RoleStore";
import {useUserStore} from "@/stores/UserStore";
import type {UserModel} from "@/models/UserModel";

export default {
  name: "RoleOverView",
  components: { RoleDetails },
  setup() {
    const roleStore = useRoleStore();
    roleStore.loadAllRoles();
    const roles = roleStore.allRoles;

    const userStore = useUserStore();
    userStore.loadUsers();
    const allUsers = userStore.users;

    return {
      roles, allUsers
    };
  },
  methods: {
    getNames(allUsers: UserModel[]): String[] {
      const userNames: String[] = [];
      allUsers.forEach(user => {
        userNames.push(`${user.getEmail()} (${user.getRole().getDisplayName()})`);
      })
      return userNames;
    }
  },
}
</script>

<style scoped>
</style>