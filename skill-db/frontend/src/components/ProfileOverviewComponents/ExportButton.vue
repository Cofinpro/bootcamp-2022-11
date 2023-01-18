<template>
  <ButtonWithTooltip tooltip="Liste exportieren"
                     v-if="isAdminOrHR"
                     icon="mdi-file-download-outline"
                     @clicked="emitToParent"/>
</template>

<script type="ts">
import {useBlobStore} from "@/stores/BlobStore";
import ButtonWithTooltip from "@/components/ProfileOverviewComponents/ButtonWithTooltip.vue";
import {useErrorStore} from "@/stores/ErrorStore";

export default {
  name: "ExportButton",
  emits: {'download:xlsx': {type: null}},
  components: {ButtonWithTooltip},
  computed: {
    isAdminOrHR() {
      return (window.localStorage.getItem('role') === 'ROLE_ADMIN' ||
          window.localStorage.getItem('role') === 'ROLE_HR');
    }
  },
  methods: {
    async emitToParent() {
      this.$emit('download:xlsx')
    }
  },
}
</script>

<style scoped>

</style>
