<template>
  <div v-for="operation in operations">
    <v-tooltip location="end top">
      <template v-slot:activator="{ props }">
        <v-icon id = "test_alert"
                v-bind="props"
                v-if="operation.getTarget() === user.getId()"
                color="red">
          mdi-alert-rhombus
        </v-icon>
      </template>
      <span id="test_span">
        {{ operationType === "changeRole" ?
          `Rolle: ${operation.getParam()}, von: ${operation.getInitiator()}` :  `von: ${operation.getInitiator()}` }}
      </span>
    </v-tooltip>
  </div>
</template>

<script setup lang="ts">
import {UserModel} from "@/models/UserModel";

const name = "AlertWithTooltip";
const props = defineProps({
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
});
</script>
