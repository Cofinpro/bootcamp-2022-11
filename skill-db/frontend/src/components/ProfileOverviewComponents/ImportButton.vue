<template>
  <ButtonWithTooltip v-if="isAdminOrHR"
                     tooltip="Profil von csv importieren"
                     icon="mdi-file-upload-outline"
                     @clicked="$refs.fileInput.click()"/>
  <input type="file"
         v-if="isAdminOrHR"
         ref="fileInput"
         style="display: none"
         @change="emitToParentAndReset"
         accept="text/csv"/>
</template>

<script type="ts">
import ButtonWithTooltip from "@/components/ProfileOverviewComponents/ButtonWithTooltip.vue";
import {useBlobStore} from "@/stores/BlobStore";
import {useOverviewStore} from "@/stores/OverviewStore";
import {computed} from "vue";

export default {
  name: "ImportButton",
  emits: {'upload:csv': {type: File}},
  components: {ButtonWithTooltip},
  setup() {
    const isAdminOrHR = computed(() => {
      return (window.localStorage.getItem('role') === 'ROLE_ADMIN' ||
          window.localStorage.getItem('role') === 'ROLE_HR');
    });

    async function emitToParentAndReset() {
      context.emit('upload:csv', this.$refs.fileInput.files[0])
      context.refs.fileInput.value = null;
    }

    return {
      isAdminOrHR,
    }
  }
}
</script>
