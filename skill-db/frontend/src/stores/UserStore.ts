import {defineStore} from "pinia";
import {ConvertToUserModel, UserModel} from "@/models/UserModel";
import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";
import {AxiosError} from "axios";

export const useUserStore = defineStore('userStore', {
    state: () => ({
        users: [] as UserModel[],
        user: new UserModel(),
        loading: Boolean(false),
        hasProfile: Boolean(false),
        profileId: String
    }),
    actions: {
        async loadUsers(): Promise<void> {
            this.loading = true;
            const errorStore = useErrorStore();
            await axiosInstance.get("/api/v1/users").then(response => {
                if (this.users.length > 0) {
                    this.users = [];
                }
                response.data.forEach((element: object) => {
                    this.users.push(ConvertToUserModel.toUserModel(element));
                })
            }).catch((error) => {
                errorStore.catchAllAxiosErrors(error, 'GetAll', '');
            })
        },

        async checkForExistingUserProfile(userId: string): Promise<void> {
            this.loading = true;
            const errorStore = useErrorStore();
            await axiosInstance.get(`/api/v1/users/${userId}/profile/exists`).then(response => {
                    this.hasProfile = response.data;
                }
            ).catch((error) => {
                errorStore.catchAllAxiosErrors(error, 'GetProfile', userId);
            });
            this.loading = false;
        },

        async getProfileIdFromUser(userId: string): Promise<void> {
            this.loading = true;
            const errorStore = useErrorStore();
            await axiosInstance.get(`/api/v1/users/${userId}/profile`).then(response => {
                    this.profileId = response.data;
                }
            ).catch((error) => {
                errorStore.catchAllAxiosErrors(error, 'GetId', userId);
            });
            this.loading = false;
        },

        async lockUser(userId: string): Promise<void> {
            this.loading = true;
            const errorStore = useErrorStore()
            await axiosInstance.patch(`/api/v1/users/${userId}/lock`).then((response) => {
                if (response.status === 202) {
                    const error = new AxiosError(response.data.message, String(response.status));
                    errorStore.catchCustomAxios(error);
                } else {
                    errorStore.toggleHasError();
                }
            }).catch((error) => {
                errorStore.catchAllAxiosErrors(error, 'PostPatch','');
            });
            this.loading = false;
        },

    }
})
