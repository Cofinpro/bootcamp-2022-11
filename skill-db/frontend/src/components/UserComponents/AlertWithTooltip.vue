<template>
  <div v-for="operation in userStore.roleChangeOperations">
    <v-tooltip>
      <template v-slot:activator="{ props }">
        <v-icon v-bind="props"
                v-if="operation.getTarget() === user.getId()"
                color="red">
          mdi-alert-rhombus
        </v-icon>
      </template>
      <span> {{ `Rolle: ${operation.getParam()}, von: ${operation.getInitiator()}` }}</span>
    </v-tooltip>
  </div>
</template>

<script lang="ts">
import {useUserStore} from "@/stores/UserStore";
import {UserModel} from "@/models/UserModel";

export default {
  name: "AlertWithTooltip",
  props: {
    user: UserModel,
  },
  data() {
    return {
      userStore: useUserStore(),
    }
  }
}
</script>

<style scoped>

</style>