<template>
  <div class="pt-0 ">
    <div class="block_title">
      Referenzen
    </div>
    <div class="block_content">
      <ul class="pl-6">
        <li v-for="reference in content.split('\n')">
          <p>{{ reference }}</p>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
const name = "References";
const props = defineProps({
  content: {
    required: true,
    type: String
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

.block_content {
  margin-top: 12px;
  font-family: "Poppins", sans-serif;
}
</style>