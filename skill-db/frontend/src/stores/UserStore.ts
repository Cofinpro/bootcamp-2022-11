import {defineStore} from "pinia";
import {ConvertToUserModel, UserModel} from "@/models/UserModel";
// import axios from "@/axios";

export const useUserStore = defineStore('userStore',{
    state: () => ({
        users: [] as UserModel[],
        loading: Boolean(false)
    }),
    actions: {
        loadDummyUser() {
            this.loading = true;
            this.users = [ConvertToUserModel.toUserModel(
                {
                    email: "Max.Mustermann@cofinpro.de",
                    role: "Admin",
                    rights: ["Lesen","Editieren","Löschen"],
                    locked: false,
                    emailConfirmed: false,
                }
            ),
            ConvertToUserModel.toUserModel(
                {
                    email: "Max.Mustermann2@cofinpro.de",
                    role: "User",
                    rights: ["Lesen"],
                    locked: true,
                    emailConfirmed: false,
                }
            )]
        }
    }
})
