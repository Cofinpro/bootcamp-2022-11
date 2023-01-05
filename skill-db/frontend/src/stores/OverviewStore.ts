import {defineStore} from "pinia";
import {ConvertToOverviewCard, OverviewModel} from "@/models/OverviewModel";
import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";

export const useOverviewStore = defineStore('OverviewStore', {
    state: () => ({
        cards: [] as OverviewModel[],
        loading: Boolean(false),
    }),
    actions: {
        loadOverview(): void {
            this.loading = true;
            const errorStore = useErrorStore();
            axiosInstance.get('/api/v1/profiles').then((response) =>{
                this.cards = [];
                response.data.forEach((element: object) => {
                    this.cards.push(ConvertToOverviewCard.toOverviewCard(element))
                });
            }).catch((error) =>{
                errorStore.catchGetAllError(error);
            });
            this.loading = false;
        }
    }
})
