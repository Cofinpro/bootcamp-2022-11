<template>
  <ButtonWithTooltip tooltip="Liste exportieren"
                     v-if="isAdminOrHR"
                     icon="mdi-file-download-outline"
                     @clicked="this.$emit('download:xlsx')"/>
</template>

<script type="ts">
import ButtonWithTooltip from "@/components/ProfileOverviewComponents/ButtonWithTooltip.vue";
import {computed} from "vue";

export default {
  name: "ExportButton",
  emits: {'download:xlsx': {type: null}},
  components: {ButtonWithTooltip},
  setup() {
    const isAdminOrHR = computed(() => {
      return (window.localStorage.getItem('role') === 'ROLE_ADMIN' ||
          window.localStorage.getItem('role') === 'ROLE_HR');
    });
    return{
      isAdminOrHR,
    }
  },
  methods: {

  },
}
</script>
