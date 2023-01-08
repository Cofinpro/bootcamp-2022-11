<template>
  <RoleDetails :roles="roles" :users="getNames(users)"/>
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
    const users = userStore.users;

    return {
      roles, users
    };
  },
  methods: {
    getNames(users: UserModel[]): String[] {
      const userNames: String[] = [];
      users.forEach(user => {
        userNames.push(user.getEmail());
      })
      return userNames;
    }
  },
}
</script>

<style scoped>
</style>