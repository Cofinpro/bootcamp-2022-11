import {defineStore} from "pinia";
import axiosInstance from "@/axios";
import {useErrorStore} from "@/stores/ErrorStore";

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
            }).catch((error) => {
                const errorStore = useErrorStore();
                errorStore.catchExportError(error);
            })
        },
        postCSV(file: File) {
            const formData = new FormData();
            formData.append('file',file)
            axiosInstance({
                url: '/api/v1/profiles/import',
                method: 'post',
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then(response => {
                console.log('sucess');
            }).catch(error => {
                console.log(error);
                }
            )
        },
    }
    },


)
