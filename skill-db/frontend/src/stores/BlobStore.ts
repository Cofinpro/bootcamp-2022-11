import {defineStore} from "pinia";
import axiosInstance from "@/axios";
import {useErrorStore} from "@/stores/ErrorStore";

export const useBlobStore = defineStore('blobStore',{
    state: () => ({
        blob: new Blob(),
    }),
    actions: {
        async getExcel(): Promise<void> {
            await axiosInstance({
                url: '/api/v1/profiles/export',
                method: 'get',
                responseType: 'blob',
            }).then((response) => {
                this.blob = response.data;
            }).catch((error) => {
                const errorStore = useErrorStore();
                errorStore.catchExportError(error);
            })
        },
        async postCSV(file: File): Promise<void> {
            const formData = new FormData();
            formData.append('file', file);
            await axiosInstance({
                url: '/api/v1/profiles/import',
                method: 'post',
                data: formData,
                headers: { 'Content-Type': 'multipart/form-data' }
            }).then(response =>{
                console.log(response);
            }).catch(error => {
                const errorStore = useErrorStore();
                console.log(error);
                errorStore.catchImportError(error);
            })
        },
    }
    },


)
