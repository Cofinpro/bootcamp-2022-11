<template>
  <v-btn class="mt-10"
         :style="!isValid ? {
                  color: '#BDBDBD !important',
                  border: '1px dashed #BBBBBB !important',
                } : ''"
         :disabled="!isValid"
         @click="update? updateProfile() : createProfile()"
         elevation="0">
    {{ update ? "Änderungen speichern" : "Profil erstellen" }}
  </v-btn>
</template>

<script>
export default {
  name: "ConfirmButton",
  props: {
    isValid:{
      type: Boolean,
      required: true,
    },
    update: {
      type: Boolean,
      required: true,
    },
    methods: {
      updateProfile() {
        this.$emit('updateProfile');
      },
      createProfile() {
        this.$emit('createProfile');
      },
    }
  }
}
</script>

<style scoped>

</style>
