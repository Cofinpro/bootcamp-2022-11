import {defineStore} from "pinia";
import {ConvertToOverviewCard, OverviewModel} from "@/models/OverviewModel";
import axios from "@/axios";
import {useErrorStore} from "@/stores/ErrorStore";

export const useOverviewStore = defineStore('OverviewStore', {
    state: () => ({
        cards: [] as OverviewModel[],
        loading: Boolean(false),
    }),
    actions: {
        loadOverview(): void {
            this.loading = true;
            const errorStore = useErrorStore();
            axios.get('/api/v1/profiles').then((response) =>{
                this.cards = [];
                response.data.forEach((element: object) => {
                    this.cards.push(ConvertToOverviewCard.toOverviewCard(element))
                });
            }).catch((error) =>{
                errorStore.catchOverviewError(error);
            });
            this.loading = false;
        }
    }
})
