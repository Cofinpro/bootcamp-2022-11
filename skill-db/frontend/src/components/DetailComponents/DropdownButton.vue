x<template>

  <v-menu :close-on-content-click="false">
    <template v-slot:activator="{ props }">
      <v-btn :disabled="state.auth()<0"
             class="pa-0" v-bind="props"
             min-width="40px" width="40px"
             height="35px" elevation="0">
        <v-icon size="large">
          mdi-cog
        </v-icon>
      </v-btn>
    </template>

    <v-list>
      <v-list-item v-if="state.auth()>-1"
                   @click="state.enterEdit()">
        <v-list-item-title>
          Bearbeiten
        </v-list-item-title>
      </v-list-item>
      <v-list-item v-if="state.auth()>1"
                   @click="state.lockProfile(); toLock = !toLock;">
        <v-list-item-title>
          {{ state.ownerOfProfileIsLocked ? 'Entsperren' : 'Sperren' }}
        </v-list-item-title>
      </v-list-item>
      <v-list-item v-if="state.auth()>-1"
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
        <v-btn class="ml-1 mr-1" @click="state.deleteProfile()">
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
import {ref} from "vue";

export default {
  name: "DropdownButton",
  emits: [
    'delete',
    'edit',
    'lock'
  ],
  props: {
    state: {
      required: true
    }
  },
  async setup(props) {
    await props.state.loadLockStatusByUserId();
    return {
      toDelete: ref(false),
      toLock: ref(false)
    }
  },
}
</script>

<style scoped>
.v-btn--disabled {
  color: #BDBDBD !important;
  border: 1px dashed #BBBBBB !important;
}
</style>
