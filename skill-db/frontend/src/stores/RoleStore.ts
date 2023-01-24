import {defineStore} from "pinia";
import {ConvertToRoleModel, RoleModel} from "@/models/RoleModel";
import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";

export const useRoleStore = defineStore('roleStore', {
    state: () => ({
        details: new RoleModel(),
        allRoles: [] as RoleModel[],
        loading: Boolean(false),
        user: [] as String[],
    }),
    actions: {
        async loadAllRoles(): Promise<void> {
            this.loading = true;
            const errorStore = useErrorStore();
            await axiosInstance.get(`/api/v1/roles`).then((response) => {
                if(this.allRoles.length > 0) {
                    // reloads the list of users every time the method gets called,
                    // in case the list is not empty
                    this.allRoles = [];
                }
                response.data.forEach((element: object) => {
                    this.allRoles.push(ConvertToRoleModel.toRoleModel(element))
                });
            }).catch((error) => {
                errorStore.catchGetAllError(error);
            });
            this.loading = false;
        },
    }
})