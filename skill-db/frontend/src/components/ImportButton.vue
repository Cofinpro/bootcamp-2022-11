<template>
   <ButtonWithTooltip v-if="isAdminOrHR"
                      tooltip="Profil von csv importieren"
                     icon="mdi-file-import"
                     @clicked="showFileInput"/>
</template>

<script type="ts">
import {useBlobStore} from "@/stores/BlobStore";
import ButtonWithTooltip from "@/components/ButtonWithTooltip.vue";
import {useErrorStore} from "@/stores/ErrorStore";

export default {
  name: "ImportButton",
  components: {ButtonWithTooltip},
  computed: {
    isAdminOrHR() {
      return (window.localStorage.getItem('role') === 'ROLE_ADMIN' ||
          window.localStorage.getItem('role') === 'ROLE_HR');
    }
  },
  methods: {

  }
}
</script>

<style scoped>

</style>
