<template>
    <v-main>
      <v-container>
        <v-row>
          <v-col v-for="card in cardList" :key="card.getId()" lg="4" elevation="5" md="6">
            <OverviewCard :id="card.getId()"
                          :name="card.getName()"
                          :job-title="card.getJobTitle()"
                          :primary-skill="card.getPrimarySkill()"/>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
</template>

<script lang="ts">
import OverviewCard from "@/components/OverviewCard.vue";
import {useOverviewStore} from "@/stores/OverviewStore";

export default {
  components: {OverviewCard},
  setup() {
    const overviewStore = useOverviewStore();
    overviewStore.loadAllOverviews();
    const cardList = overviewStore.cards
    return {
      cardList,
    };
  }
}
</script>