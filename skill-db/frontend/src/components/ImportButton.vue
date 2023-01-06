<template>
   <ButtonWithTooltip v-if="isAdminOrHR"
                      tooltip="Profil von csv importieren"
                     icon="mdi-file-import"
                     @clicked="$refs.fileInput.click()"/>
  <input type="file"
         v-if="isAdminOrHR"
         ref="fileInput"
         style="display: none"
         @change="uploadCSV"
         accept="text/csv"/>
</template>

<script type="ts">
import ButtonWithTooltip from "@/components/ButtonWithTooltip.vue";
import {useBlobStore} from "@/stores/BlobStore";

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
    uploadCSV(event) {
      let blobStore = useBlobStore();
      blobStore.postCSV(this.$refs.fileInput.files[0]);
    },
  },
}
</script>

<style scoped>

</style>
