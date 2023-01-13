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
        loadRolesById(id: String) {
            this.loading = true;
            const errorStore = useErrorStore();
            axiosInstance.get(`/api/v1/roles/${id}`).then((response) => {
                this.details = ConvertToRoleModel.toRole(response.data);
            }).catch((error) => {
                errorStore.catchGetRoleError(error, id);
            });
            this.loading = false;
        },

        async loadAllRoles() {
            this.loading = true;
            const errorStore = useErrorStore();
            await axiosInstance.get(`/api/v1/roles`).then((response) => {
                if(this.allRoles.length > 0) {
                    // reloads the list of users every time the method gets called,
                    // in case the list is not empty
                    this.allRoles = [];
                }
                response.data.forEach((element: object) => {
                    this.allRoles.push(ConvertToRoleModel.toRole(element))
                });
            }).catch((error) => {
                errorStore.catchGetAllError(error);
            });
            this.loading = false;
        },
    }
})