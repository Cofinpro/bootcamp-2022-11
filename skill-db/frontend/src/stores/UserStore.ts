import {defineStore} from "pinia";
import {ConvertToUserModel, UserModel} from "@/models/UserModel";
import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";

export const useUserStore = defineStore('userStore', {
    state: () => ({
        users: [] as UserModel[],
        loading: Boolean(false),
        profile: Boolean(false),
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

        async hasProfile(userId: String) {
            this.loading = true;
            const errorStore = useErrorStore();
            await axiosInstance.get("api/v1/users/${userId}/profile").then(response => {
                this.profile = response.data;
            }
        ).catch((error) => {
            errorStore.catchGetProfileError(error, userId);
            console.log(error)
        });
            this.loading = false;
        },

        async loadUsersByRoleId(id: String) {
            this.loading = true;
            const errorStore = useErrorStore();
            await axiosInstance.get(`/api/v1/roles/${id}/users`).then((response) => {
                if(this.users.length > 0) {
                    // reloads the list of users every time the method gets called,
                    // in case the list is not empty
                    this.users = [];
                }
                response.data.forEach((element: object) => {
                    this.users.push(ConvertToUserModel.toUserModel(element));
                });
            }).catch((error) => {
                errorStore.catchGetRoleError(error, id);
                console.log(error)
            });
            this.loading = false;
        },

        async changeRole(newRole: String, id: String) {
            this.loading = true;
            const errorStore = useErrorStore()
            await axiosInstance.patch(`/api/v1/users/${id}/${newRole}`).then(() =>{
                errorStore.toggleHasError();
            }).catch((error) => {
                console.log(error);
                errorStore.catchPostPatchError(error);
            });
            this.loading = false;
        },

    }
})
