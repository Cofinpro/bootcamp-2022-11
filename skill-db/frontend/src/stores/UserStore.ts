import {defineStore} from "pinia";
import {ConvertToUserModel, UserModel} from "@/models/UserModel";
import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";
import {AxiosError} from "axios";
import {ConvertToOperationsModel, OperationsModel} from "@/models/OperationsModel";

export const useUserStore = defineStore('userStore', {
    state: () => ({
        users: [] as UserModel[],
        loading: Boolean(false),
        hasProfile: Boolean(false),
        profileId: String,
        roleChangeOperations: [] as OperationsModel[],
        lockUserOperations: [] as OperationsModel[],
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

        async checkForExistingUserProfile(userId: String) {
            this.loading = true;
            const errorStore = useErrorStore();
            await axiosInstance.get(`api/v1/users/${userId}/profile/exists`).then(response => {
                this.hasProfile = response.data;
            }
        ).catch((error) => {
            errorStore.catchGetProfileError(error, userId);
        });
            this.loading = false;
        },

        async getProfileIdFromUser(userId: String) {
            this.loading = true;
            const errorStore = useErrorStore();
            await axiosInstance.get(`api/v1/users/${userId}/profile`).then(response => {
                    this.profileId = response.data;
                }
            ).catch((error) => {
                errorStore.catchGetProfileError(error, userId);
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
            });
            this.loading = false;
        },

        async getPendingRoleChanges() {
            this.loading = true;
            const errorStore = useErrorStore();
            await axiosInstance.get(`/api/v1/users/pending/role`).then((response) => {
                if(this.roleChangeOperations.length > 0) {
                    // reloads the list of users every time the method gets called,
                    // in case the list is not empty
                    this.roleChangeOperations = [];
                }
                response.data.forEach((element: object) => {
                    this.roleChangeOperations.push(ConvertToOperationsModel.toOperationsModel(element));
                });
            }).catch((error) => {
                errorStore.catchGetAllError(error);
            });
            this.loading = false;
        },

        async getPendingLockChanges() {
            this.loading = true;
            const errorStore = useErrorStore();
            await axiosInstance.get(`/api/v1/users/pending/lock`).then((response) => {
                if(this.lockUserOperations.length > 0) {
                    // reloads the list of users every time the method gets called,
                    // in case the list is not empty
                    this.lockUserOperations = [];
                }
                response.data.forEach((element: object) => {
                    this.lockUserOperations.push(ConvertToOperationsModel.toOperationsModel(element));
                });
            }).catch((error) => {
                errorStore.catchGetAllError(error);
            });
            this.loading = false;
        },

        async changeRole(id: String, newRole: String) {
            this.loading = true;
            const errorStore = useErrorStore()
            await axiosInstance.patch(`/api/v1/users/${id}/${newRole}`).then((response) =>{
                if(response.status === 202) {
                    throw new AxiosError(response.data.message, String(response.status));
                } else {
                    errorStore.toggleHasError();
                }
            }).catch((error) => {
                errorStore.catchPostPatchError(error);
            });
            this.loading = false;
        },

        async lockUser(userId: String) {
            this.loading = true;
            const errorStore = useErrorStore()
            await axiosInstance.patch(`/api/v1/users/${userId}/lock`).then((response) =>{
                if(response.status === 202) {
                    throw new AxiosError(response.data.message, String(response.status));
                } else {
                    errorStore.toggleHasError();
                }
            }).catch((error) => {
                errorStore.catchPostPatchError(error);
            });
            this.loading = false;
        },

    }
})
