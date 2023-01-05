import {defineStore} from "pinia";
import axiosInstance from "@/axios";

export const useBlobStore = defineStore('blobStore',{
    state: () => ({
        blob: new Blob(),
    }),
    actions: {
        async getExcel() {
            await axiosInstance({
                url: '/api/v1/profiles/export',
                method: 'get',
                responseType: 'blob',
            }).then((response) => {
                this.blob = response.data;
                console.log(response.data);
            })
        }
    }
    },


)
