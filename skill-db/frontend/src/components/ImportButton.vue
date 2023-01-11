<template>
   <ButtonWithTooltip v-if="isAdminOrHR"
                      tooltip="Profil von csv importieren"
                     icon="mdi-file-upload-outline"
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
import {useOverviewStore} from "@/stores/OverviewStore";

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
    async uploadCSV(event) {
      const blobStore = useBlobStore();
      await blobStore.postCSV(this.$refs.fileInput.files[0]);
      this.$refs.fileInput.value = null;
      const overviewStore = useOverviewStore();
      overviewStore.loadOverview();
    },
  },
}
</script>

<style scoped>

</style>
