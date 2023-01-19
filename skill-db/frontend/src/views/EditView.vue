<template>
    <EditComponent :update="true"/>
</template>

<script lang="ts">
import EditComponent from "@/components/EditComponent.vue";

export default {
  name: "EditView",
  components: { EditComponent },
}
</script>

<style scoped>
</style>
