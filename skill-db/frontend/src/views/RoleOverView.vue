<template>
  <RoleDetails :roles="roles"/>
</template>

<script lang="ts">
import RoleDetails from "@/components/RoleDetails.vue";
import {useRoleStore} from "@/stores/RoleStore";

export default {
  name: "RoleOverView",
  components: { RoleDetails },
  setup() {
    const roleStore = useRoleStore();
    roleStore.loadAllRoles();
    const roles = roleStore.allRoles;

    return {
      roles
    };
  },
}
</script>

<style scoped>
</style>