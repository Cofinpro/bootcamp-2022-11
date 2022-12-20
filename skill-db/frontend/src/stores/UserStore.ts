import {defineStore} from "pinia";
import {ConvertToUserModel, UserModel} from "@/models/UserModel";
import axios from "@/axios";

export const useUserStore = defineStore('userStore',{
    state: () => ({
        users: [] as UserModel[],
        loading: Boolean(false)
    }),
    actions: {
        loadUsers(): void {
            this.loading = true;
            axios.get("/api/v1/users").then(response => {
                this.users = [];
                response.data.forEach((element: object) => {
                    this.users.push(ConvertToUserModel.toUserModel(element));
                })
                //this.users.forEach(user => console.log(user));
            } )
        }

    }
})
