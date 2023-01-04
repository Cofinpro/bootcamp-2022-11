import {defineStore} from "pinia";
import {ConvertToRoleModel, RoleModel} from "@/models/RoleModel";
import axios from "@/axios";
import {useErrorStore} from "@/stores/ErrorStore";

export const useDetailStore = defineStore('detailStore', {
    state: () => ({
        details: new RoleModel(),
        allRoles: [] as RoleModel[],
        loading: Boolean(false),
    }),
    actions: {
        loadRolesById(id: String) {
            this.loading = true;
            const errorStore = useErrorStore();
            axios.get(`/api/v1/roles/${id}`).then((response) => {
                this.details = ConvertToRoleModel.toRole(response.data);
            }).catch((error) => {
                errorStore.catchGetRoleError(error, id);
                console.log(error)
            });
            this.loading = false;
        },

        loadAllRoles() {
            this.loading = true;
            const errorStore = useErrorStore();
            axios.get(`/api/v1/roles`).then((response) => {
                this.allRoles = [];
                response.data.forEach((element: object) => {
                    this.allRoles.push(ConvertToRoleModel.toRole(element))
                });
            }).catch((error) => {
                errorStore.catchGetAllError(error);
                console.log(error)
            });
            this.loading = false;
        },
    }
})