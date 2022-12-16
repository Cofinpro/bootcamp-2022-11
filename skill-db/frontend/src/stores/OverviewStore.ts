import {defineStore} from "pinia";
import {ConvertToOverviewCard, OverviewModel} from "@/models/OverviewModel";
import axios from "@/axios";

export const useOverviewStore = defineStore('OverviewStore', {
    state: () => ({
        cards: [] as OverviewModel[],
        loading: Boolean(false),
    }),
    actions: {
        loadOverview(): void {
            this.loading = true;
            axios.get('/api/v1/profile').then((response) =>{
                response.data.forEach((element: object) => {
                    this.cards.push(ConvertToOverviewCard.toOverviewCard(element))
                })
            }).catch((error) =>{
                console.log(error);
            });
            this.loading = false;
        },
        loadDummyOverview() {
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
