<template>
  <ButtonWithTooltip :disabled="hasProfile"
                     tooltip="Neues Profil erstellen"
                     icon="mdi-plus-thick"
                     @clicked="$router.push(`/details/new`)"/>
</template>

<script lang="ts">
import ButtonWithTooltip from "@/components/ProfileOverviewComponents/ButtonWithTooltip.vue";
import {useUserStore} from "@/stores/UserStore";

export default {
  name: "CreateButton",
  components: { ButtonWithTooltip },
  setup() {
    const userStore = useUserStore();
    return {
      userStore
    }
  },
  computed: {
    hasProfile(): boolean {
      const userId = window.localStorage.getItem('user_id');
      this.userStore.checkForExistingUserProfile(userId);
      return this.userStore.hasProfile;
    }
  },
}
</script>

<style scoped>

</style>
