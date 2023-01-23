<template>

  <v-menu :close-on-content-click="false">
    <template v-slot:activator="{ props }">
      <v-btn :disabled="disabled"
             :style="disabled ? {
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
      <v-list-item @click="editFunction.method">
        <v-list-item-title>
          {{ editFunction.name }}
        </v-list-item-title>
      </v-list-item>
      <v-list-item v-if="lockFunction"
                   @click="lockFunction.method(); toLock = !toLock;">
        <v-list-item-title>
          {{ lockFunction.name }}
        </v-list-item-title>
      </v-list-item>
      <v-list-item @click="toDelete = !toDelete">
        <v-list-item-title>
          {{ deleteFunction.name }}
        </v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>

  <v-dialog max-width="200" v-model="toDelete">
    <v-card>
      <v-card-text>Willst du dieses Profil wirklich löschen?</v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn class="ml-1 mr-1" @click="deleteFunction.method">
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
import {ref} from "vue";

export default {
  name: "DropdownButton",
  props: {
    editFunction: {
      required: false
    },
    lockFunction: {
      default: undefined,
      required: false
    },
    deleteFunction: {
      required: false
    },
    disabled: {
      required: false,
      default: false
    }
  },
  setup() {
    return {
      userStore: useUserStore(),
      toDelete: ref(false),
      toLock: ref(false)
    }
  },
}
</script>

<style scoped>
</style>
