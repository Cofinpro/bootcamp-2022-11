<template>
  <v-snackbar
  v-model="errorStore.hasError"
  multi-line
  color="red"
  timeout="10000"
  >
    <div v-if="errorStore.errorText !== ''">
      {{errorStore.errorText}}
    </div>
    <div v-else>
      Unknown Error
    </div>
  </v-snackbar>
</template>

<script>
import {useErrorStore} from "@/stores/ErrorStore";

export default {

  data: () => ({
    errorStore: useErrorStore(),
  })
}
</script>

<style scoped>

</style>
