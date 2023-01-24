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

<script lang="ts">
import {useErrorStore} from "@/stores/ErrorStore";

export default {
  setup() {
    return {
      errorStore: useErrorStore()
    }
  }
}
</script>

<style scoped>

</style>
