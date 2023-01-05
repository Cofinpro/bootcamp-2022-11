import {defineStore} from "pinia";
import {ConvertToUserModel, UserModel} from "@/models/UserModel";
import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";

export const useUserStore = defineStore('userStore', {
    state: () => ({
        users: [] as UserModel[],
        loading: Boolean(false)
    }),
    actions: {
        loadUsers(): void {
            this.loading = true;
            const errorStore = useErrorStore();
            axiosInstance.get("/api/v1/users").then(response => {
                if(this.users.length > 0) {
                    // reloads the list of users every time the method gets called,
                    // in case the list is not empty
                    this.users = [];
                }
                response.data.forEach((element: object) => {
                    this.users.push(ConvertToUserModel.toUserModel(element));
                })
            }).catch((error) => {
                errorStore.catchGetAllError(error);
            })
        },

        async changeRole(newRole: String, id: String) {
            this.loading = true;
            const errorStore = useErrorStore()
            await axiosInstance.patch(`/api/v1/users/${id}/role`,
                {
                    'shortName': newRole,
                }).then(() =>{
                errorStore.toggleHasError();
            }).catch((error) => {
                console.log(error);
                errorStore.catchPostPatchError(error);
            });
            this.loading = false;
        },

    }
})
