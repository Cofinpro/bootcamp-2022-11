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
        async loadOverview(): void {
            this.loading = true;
            const errorStore = useErrorStore();
            await axiosInstance.get('/api/v1/profiles').then((response) => {
                this.cards = [];
                for (const element of response.data) {
                    this.cards.push(ConvertToOverviewCard.toOverviewCard(element))
                }
            }).catch((error) => {
                errorStore.catchUserOverviewError(error);
            });
            await this.loadAllProfilePics();
            this.loading = false;
        },
        async loadAllProfilePics(): Promise<void> {
            const errorStore = useErrorStore();
            for (const element of this.cards){
                if (element.getProfilePicId()) {
                    await axiosInstance({
                        url: `/api/v1/images/${element.getProfilePicId()}`,
                        method: 'get',
                        responseType: 'blob'
                    }).then((response) => {
                        element.setProfilePic(URL.createObjectURL(response.data));
                    }).catch((error) => {
                        console.log(error);
                        errorStore.catchDownloadImageError(error);
                    })
                }
            }
        },
    }
})
