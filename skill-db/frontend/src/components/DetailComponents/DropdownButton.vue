<template>

  <v-menu :close-on-content-click="false">
    <template v-slot:activator="{ props }">
      <v-btn :disabled="authenticated<0"
             :style="authenticated<0 ? {
             color: '#BDBDBD !important',
             border: '1px dashed #BBBBBB !important'} : ''"
      class="pa-0" v-bind="props"
      min-width="40px" width="40px"
      height="35px" elevation="0">
      <v-icon size="large">
        mdi-cog
      </v-icon>
      </v-btn>
    </template>

    <v-list>
      <v-list-item v-if="authenticated>-1"
                   @click="this.$emit('edit')">
        <v-list-item-title>
          Bearbeiten
        </v-list-item-title>
      </v-list-item>
      <v-list-item v-if="authenticated>1"
                   @click="this.$emit('lock'); toLock = !toLock;">
        <v-list-item-title>
          Sperren
        </v-list-item-title>
      </v-list-item>
      <v-list-item v-if="authenticated>-1"
                   @click="toDelete = !toDelete">
        <v-list-item-title>
          Löschen
        </v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>

  <v-dialog max-width="200" v-model="toDelete">
    <v-card>
      <v-card-text>Willst du dieses Profil wirklich löschen?</v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn class="ml-1 mr-1" @click="this.$emit('delete')">
          Ja
        </v-btn>
        <v-btn class="ml-1 mr-1" @click="toDelete = !toDelete">
          Nein
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <v-overlay v-model="toLock" absolute/>
</template>

<script lang="ts">
import {useUserStore} from "@/stores/UserStore";
import {computed, ComputedRef, ref} from "vue";
import {auth} from "./DropDownFunctions";

export default {
  name: "DropdownButton",
  emits: [
    'delete',
    'edit',
    'lock'
  ],
  setup() {

    const authenticated: ComputedRef<number> = computed(() => auth());

    return {
      userStore: useUserStore(),
      toDelete: ref(false),
      toLock: ref(false),
      authenticated: authenticated.value
    }
  },
}
</script>

<style scoped>
</style>
