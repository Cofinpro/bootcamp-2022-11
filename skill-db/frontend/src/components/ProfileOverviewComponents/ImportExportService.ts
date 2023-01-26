import axiosInstance from "@/axios";
import {useErrorStore} from "@/stores/ErrorStore";
import {useOverviewStore} from "@/stores/OverviewStore";

export async function downloadXLSX() {
    const errorStore = useErrorStore();
    const blob: Blob = await getExcel();
    if (!errorStore.hasError) {
        const downloadUrl = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = downloadUrl;
        link.setAttribute('download', 'profile.xlsx');
        document.body.appendChild(link);
        link.click();
        link.remove();
    }
}
async function getExcel(): Promise<Blob> {
    let blob = new Blob()
    await axiosInstance({
        url: '/api/v1/profiles/export',
        method: 'get',
        responseType: 'blob',
    }).then((response) => {
        blob = response.data;
    }).catch((error) => {
        const errorStore = useErrorStore();
        errorStore.catchExportError(error);
    });
    return blob;
}
export async function postCSVAndReload(file: File): Promise<void> {
    const formData = new FormData();
    formData.append('file', file);
    await axiosInstance({
        url: '/api/v1/profiles/import',
        method: 'post',
        data: formData,
        headers: { 'Content-Type': 'multipart/form-data' }
    }).catch(error => {
        const errorStore = useErrorStore();
        console.log(error);
        errorStore.catchImportError(error);
    });
    const overviewStore = useOverviewStore();
    await overviewStore.loadOverview();
}
