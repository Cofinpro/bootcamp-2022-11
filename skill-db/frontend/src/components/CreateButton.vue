<template>
  <ButtonWithTooltip v-if="hasNoProfile"
                     tooltip="Neues Profil erstellen"
                     icon="mdi-plus-thick"
                     @clicked="$router.push(`/details/new`)"/>
</template>

<script lang="ts">
import ButtonWithTooltip from "@/components/ButtonWithTooltip.vue";
import {useUserStore} from "@/stores/UserStore";

export default {
  name: "CreateButton",
  components: { ButtonWithTooltip },
  setup() {
    const userId = window.localStorage.getItem('user_id');
    const userStore = useUserStore();
    return {
      userStore, userId
    }
  },
  computed: {
    hasNoProfile(): boolean {
      this.userStore.hasProfile(this.userId);
      return (!this.userStore.profile);
    }
  },
}
</script>

<style scoped>

</style>