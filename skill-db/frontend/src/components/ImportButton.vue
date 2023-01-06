<template>
   <ButtonWithTooltip v-if="isAdminOrHR"
                      tooltip="Profil von csv importieren"
                     icon="mdi-file-import"
                     @clicked="$refs.file.click()"/>
  <input type="file" ref="file" style="display: none" />
</template>

<script type="ts">
import ButtonWithTooltip from "@/components/ButtonWithTooltip.vue";

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
