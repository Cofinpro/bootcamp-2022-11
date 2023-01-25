<template>
  <ButtonWithTooltip :disabled="userStore.hasProfile"
                     tooltip="Neues Profil erstellen"
                     icon="mdi-plus-thick"
                     @clicked="$emit('create:Profile')"/>
</template>

<script lang="ts">
import ButtonWithTooltip from "@/components/ProfileOverviewComponents/ButtonWithTooltip.vue";
import {useUserStore} from "@/stores/UserStore";
import {computed} from "vue";

export default {
  name: "CreateButton",
  emits: {'create:Profile':{type:null}},
  components: { ButtonWithTooltip },
  setup() {
    const userStore = useUserStore();
    return {
      userStore
    }
  },
}
</script>
