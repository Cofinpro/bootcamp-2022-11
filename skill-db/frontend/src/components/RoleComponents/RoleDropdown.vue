<template>
  <v-card elevation="2">
    <div class="d-flex justify-space-between">
      <v-card-title>
        {{ state.role.getDisplayName() }}
      </v-card-title>
      <v-card-actions>
        <v-btn class="mr-2 mt-2" @click="this.$emit('clicked')"
               elevation="0" size="small">
          Bestätigen
        </v-btn>
      </v-card-actions>
    </div>

    <v-card-item class="d-flex flex-column justify-space-between">

      <v-select v-model="state.selectedUsersWithRole"
                :items="state.allUsersWithRole"
                label="Wähle Nutzer"
                multiple
                class="ml-5 mr-5"
                hide-details>

        <template v-slot:selection="{ item, index }">
          <v-chip v-if="index <= 0">
            <span>{{ item.title }}</span>
          </v-chip>
          <span v-if="index === 1"
                class="grey--text text-caption">
                    (+{{ state.selectedUsersWithRole.length - 1 }} mehr)
                  </span>
        </template>

      </v-select>

    </v-card-item>
  </v-card>
</template>

<script setup lang="ts">
import {RoleComponentState} from "@/components/RoleComponents/RoleComponentState";

const props = defineProps({state: RoleComponentState});
props.state?.attachRole();
</script>

<style scoped>

.v-card {
  margin-top: 20vh;
  margin-left: 20vw;
  margin-right: 2vw;
  max-width: 600px;
  min-width: 200px;
}

.v-select {
  max-width: 500px;
  min-width: 200px;
}
</style>
