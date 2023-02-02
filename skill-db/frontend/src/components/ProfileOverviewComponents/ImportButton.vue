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

<script setup lang="ts">
import ButtonWithTooltip from "@/components/ProfileOverviewComponents/ButtonWithTooltip.vue";
import {computed, ref} from "vue";
const fileInput = ref();
const emits = defineEmits(['upload:csv']);

const isAdminOrHR = computed(() => {
  return (window.localStorage.getItem('role') === 'ROLE_ADMIN' ||
      window.localStorage.getItem('role') === 'ROLE_HR');
});

async function emitToParentAndReset() {
  emits('upload:csv', fileInput.value.files[0])
  fileInput.value = undefined;
}

</script>
