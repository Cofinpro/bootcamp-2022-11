<template>
    <EditComponent :update="true"/>
</template>

<script lang="ts">
import EditComponent from "@/components/EditComponent.vue";
import {useDetailStore} from "@/stores/DetailStore";
import {useRoute} from "vue-router";

export default {
  name: "EditView",
  components: { EditComponent },
}
</script>

<style scoped>
</style>
