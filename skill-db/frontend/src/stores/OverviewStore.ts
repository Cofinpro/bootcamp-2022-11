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
            axios.get('/api/v1/profiles').then((response) =>{
                this.cards = [];
                response.data.forEach((element: object) => {
                    this.cards.push(ConvertToOverviewCard.toOverviewCard(element))
                });
            }).catch((error) =>{
                console.log(error);
            });
            this.loading = false;
        }
    }
})
