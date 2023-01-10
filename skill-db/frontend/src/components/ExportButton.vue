<template>
   <ButtonWithTooltip tooltip="Liste exportieren"
                      v-if="isAdminOrHR"
                      icon="mdi-file-export"
                     @clicked="exportProfiles"/>
</template>

<script type="ts">
import {useBlobStore} from "@/stores/BlobStore";
import ButtonWithTooltip from "@/components/ButtonWithTooltip.vue";
import {useErrorStore} from "@/stores/ErrorStore";

export default {
  name: "ExportButton",
  components: {ButtonWithTooltip},
  computed: {
    isAdminOrHR() {
      return (window.localStorage.getItem('role') === 'ROLE_ADMIN' ||
          window.localStorage.getItem('role') === 'ROLE_HR');
    }
  },
  methods: {
    async exportProfiles() {
      const blobStore = useBlobStore();
      const errorStore = useErrorStore();
      await blobStore.getExcel();
      if (! errorStore.hasError) {
        const downloadUrl = window.URL.createObjectURL(blobStore.blob);
        const link = document.createElement('a');
        link.href = downloadUrl;
        link.setAttribute('download', 'profile.xlsx');
        document.body.appendChild(link);
        link.click();
        link.remove();
      }
    },
  }
}
</script>

<style scoped>

</style>
