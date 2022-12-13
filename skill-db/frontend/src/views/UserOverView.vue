<template>
  <UserDetails :users="users"></UserDetails>
</template>

<script>
import UserDetails from "@/components/UserDetails.vue";
import {useUserStore} from "@/stores/UserStore";
export default {
  name: "UserIOverView",
  components: {UserDetails},
  setup() {
    const userStore = useUserStore();
    userStore.loadDummyUser();
    const users = userStore.users;
    return {
      users,
    };
  }
}
</script>

<style scoped>

</style>
