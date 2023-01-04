<template>
  <UserDetails :users="users"/>
</template>

<script lang="ts">
import UserDetails from "@/components/UserDetails.vue";
import {useUserStore} from "@/stores/UserStore";
export default {
  name: "UserOverView",
  components: { UserDetails },
  setup() {
    const userStore = useUserStore();
    userStore.loadUsers();
    const users = userStore.users;
    return {
      users,
    };
  }
}
</script>

<style scoped>
</style>
