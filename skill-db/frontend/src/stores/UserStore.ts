import {defineStore} from "pinia";
import {ConvertToUserModel, UserModel} from "@/models/UserModel";
import axios from "@/axios";
import {useErrorStore} from "@/stores/ErrorStore";

export const useUserStore = defineStore('userStore', {
    state: () => ({
        users: [] as UserModel[],
        loading: Boolean(false)
    }),
    actions: {
        loadUsers(): void {
            this.loading = true;
            const errorStore = useErrorStore();
            axios.get("/api/v1/users").then(response => {
                if(this.users.length > 0) {
                    // reloads the list of users every time the method gets called,
                    // in case the list is not empty
                    this.users = [];
                }
                response.data.forEach((element: object) => {
                    this.users.push(ConvertToUserModel.toUserModel(element));
                })
            }).catch((error) => {
                errorStore.catchOverviewError(error);
            })
        }

    }
})
