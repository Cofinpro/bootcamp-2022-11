<template>
  <v-snackbar v-model="errorStore.hasError" multi-line
              color="red" timeout="4500">
    <div class="d-flex justify-center"
         v-if="errorStore.errorText !== ''">
      {{ errorStore.errorText }}
    </div>
    <div v-else>
      Unknown Error
    </div>
  </v-snackbar>
</template>

<script setup lang="ts">
import {useErrorStore} from "@/stores/ErrorStore";

const errorStore = useErrorStore();
</script>
