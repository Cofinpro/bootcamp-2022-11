<template>
  <div>
   <ButtonWithTooltip tooltip="Liste exportieren"
                     icon="mdi-file-export"
                     @clicked="exportProfiles"/>
  </div>
</template>

<script type="ts">
import {useBlobStore} from "@/stores/BlobStore";
import ButtonWithTooltip from "@/components/ButtonWithTooltip.vue";

export default {
  name: "ExportButton",
  components: {ButtonWithTooltip},
  methods: {
    async exportProfiles() {
      const blobStore = useBlobStore();
      await blobStore.getExcel();
      const downloadUrl = window.URL.createObjectURL(blobStore.blob);
      const link = document.createElement('a');
      link.href = downloadUrl;
      link.setAttribute('download', 'profile.xlsx');
      document.body.appendChild(link);
      link.click();
      link.remove();
    },
  }
}
</script>

<style scoped>

</style>
