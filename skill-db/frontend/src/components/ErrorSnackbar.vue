<template>
  <v-snackbar
  v-model="detailStore.hasError"
  color="red"
  timeout="5000"
  >
    <div v-if="detailStore.errorText !== ''">
      {{detailStore.errorText}}
    </div>
    <div v-else>
      Unknown Error
    </div>
  </v-snackbar>
</template>

<script>
import {useDetailStore} from "@/stores/DetailStore";

export default {

  data: () => ({
    detailStore: useDetailStore(),
  })
}
</script>

<style scoped>

</style>
