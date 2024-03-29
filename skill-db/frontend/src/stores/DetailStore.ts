import {defineStore} from "pinia";
import {ConvertResponseToDetailModel, DetailModel} from "@/models/DetailModel";
import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";

export const useDetailStore = defineStore('detailStore', {
        state: () => ({
            details: new DetailModel(),
            profilePic: '',
            loading: Boolean(false),
        }),

        actions: {
            async loadDetailsById(id: string): Promise<void> {
                this.loading = true;
                let profilePicId = null;
                const errorStore = useErrorStore();
                await axiosInstance.get(`/api/v1/profiles/${id}`).then((response) => {
                    this.details = ConvertResponseToDetailModel.toDetailModel(response.data);
                    profilePicId = response.data.profilePicId;
                }).catch((error) => {
                    errorStore.catchAllAxiosErrors(error, 'GetId', id);
                });
                await axiosInstance({
                    url: `/api/v1/images/${profilePicId}`,
                    method: 'get',
                    responseType: 'blob'
                }).then((response) => {
                    if (response.data.size === 0) {
                        this.profilePic = '';
                    } else {
                        this.profilePic = URL.createObjectURL(response.data);
                    }
                }).catch((error) => {
                    console.log(error);
                    errorStore.catchAllAxiosErrors(error, 'DownloadImage', '');
                })
                this.loading = false;
            },
        },
    }
)
