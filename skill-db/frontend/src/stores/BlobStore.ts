import {defineStore} from "pinia";
import axiosInstance from "@/axios";

export const useBlobStore = defineStore('blobStore',{
    state: () => ({
        blob: new Blob(),
    }),
    actions: {
        async getExcel() {
            await axiosInstance
                .get('/api/v1/profiles/export')
                .then((response) => {
                    this.blob=response.data;
                })
        }
    }
    },


)
