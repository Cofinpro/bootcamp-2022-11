<template>
  <div v-for="operation in operations">
    <v-tooltip>
      <template v-slot:activator="{ props }">
        <v-icon v-bind="props"
                v-if="operation.getTarget() === user.getId()"
                color="red">
          mdi-alert-rhombus
        </v-icon>
      </template>
      <span>
        {{ operationType === "changeRole" ?
          `Rolle: ${operation.getParam()}, von: ${operation.getInitiator()}` :  `von: ${operation.getInitiator()}` }}
      </span>
    </v-tooltip>
  </div>
</template>

<script lang="ts">
import {UserModel} from "@/models/UserModel";
import type {OperationsModel} from "@/models/OperationsModel";

export default {
  name: "AlertWithTooltip",
  props: {
    user: {
      required: true,
      type: UserModel
    },
    operationType: {
      required: true,
      type: String
    },
    operations: {
      required: true,
      type: Array
    }
  },
}
</script>

<style scoped>
</style>
