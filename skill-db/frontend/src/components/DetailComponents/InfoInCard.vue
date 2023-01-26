<template>
  <v-card style="border: 1px solid lightgray" max-width="275" min-width="275">
    <p class="block_title pa-2">
      {{ info }}
    </p>
    <div v-if="multiple && chips">
      <div class="d-flex flex-wrap justify-start pa-2">
        <div v-for="item in content" class="pa-1">
          <v-chip>{{ item }}</v-chip>
        </div>
      </div>
    </div>
    <div v-else>
      <p class="pl-3 pa-2">
        {{ content }}
      </p>
    </div>
  </v-card>

</template>

<script setup lang="ts">
const props = defineProps({
  info: {
    required: true,
    type: String
  },
  content: {
    required: true
  },
  chips: {
    required: false,
    default: false,
    type: Boolean
  },
  multiple: {
    required: false,
    default: false,
    type: Boolean
  }
});
</script>

<style scoped>

.block_title {
  font-weight: bold;
  line-height: 20px;
  letter-spacing: 2px;
  font-family: "Poppins", sans-serif;
}
</style>