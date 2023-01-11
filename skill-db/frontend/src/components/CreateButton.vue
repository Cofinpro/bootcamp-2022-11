<template>
  <ButtonWithTooltip v-if="hasNoProfile"
                     tooltip="Neues Profil erstellen"
                     icon="mdi-plus-thick"
                     @clicked="createProfile"/>
</template>

<script>
import ButtonWithTooltip from "@/components/ButtonWithTooltip.vue";
import router from "@/router";
import {useUserStore} from "@/stores/UserStore";

export default {
  name: "CreateButton",
  components: { ButtonWithTooltip },
  setup() {
    const userId = window.localStorage.getItem('user_id');
    const userStore = useUserStore();
    userStore.hasProfile(userId);
    return {
      userStore
    }
  },
  computed: {
    hasNoProfile() {
      return (!this.userStore.profile);
    }
  },
  methods: {
    createProfile() {
      router.push(`/details/new`);
    },
  }
}
</script>

<style scoped>

</style>