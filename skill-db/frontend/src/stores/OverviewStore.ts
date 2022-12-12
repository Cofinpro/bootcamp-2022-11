import {defineStore} from "pinia";
import {ConvertToOverviewCard, OverviewModel} from "@/models/OverviewModel";

export const useOverviewStore = defineStore('OverviewStore', {
    state: () => ({
        cards: [] as OverviewModel[],
        loading: Boolean(false),
    }),
    actions: {
        loadAllOverviews() {
            this.loading = true;
            this.cards = [
                ConvertToOverviewCard.toOverviewCard(
                    {
                        id: 0,
                        name: "HI MAX",
                        jobTitle: "Consultant",
                        primarySkill: "Tech"
                    }
                ),
                ConvertToOverviewCard.toOverviewCard(
                    {
                        id: 1,
                        name: "HI MAX2",
                        jobTitle: "Consultant",
                        primarySkill: "Tech"
                    }
                )
            ];
            this.loading = false;
        }
    }
})