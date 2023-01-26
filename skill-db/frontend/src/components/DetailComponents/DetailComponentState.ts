import router from "@/router";
import {useUserStore} from "@/stores/UserStore";
import {useDetailStore} from "@/stores/DetailStore";
import {useRoute} from "vue-router";
import {DetailModel} from "@/models/DetailModel";
import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";

export class DetailComponentState {

    details: DetailModel;
    profilePic: string;
    profileId: string;
    role: string;
    ownerOfProfileIsLocked: boolean;

    constructor(profileId: string) {
        this.details = new DetailModel();
        this.profilePic = '';
        this.profileId = profileId;
        this.role = window.localStorage.getItem('role');
        this.ownerOfProfileIsLocked = true;
    }

    async setupDetails(): Promise<void> {
        const detailStore = useDetailStore();
        console.log(this.profileId);
        await detailStore.loadDetailsById(this.profileId);
        this.details = detailStore.details;
        this.profilePic = detailStore.profilePic;
    }

    async loadLockStatusByUserId(): Promise<void> {
        if (this.auth() > 1) {
            const id = this.details.getOwnerId();
            const errorStore = useErrorStore();
            await axiosInstance.get(`/api/v1/users/${id}/locked`).then(response => {
                this.ownerOfProfileIsLocked = Boolean(response.data);
            }).catch((error) => {
                errorStore.catchGetProfileError(error, id);
            })
        }
    }

    auth(): number {
        if (this.role === "ROLE_ADMIN") {
            return 2;
        } else if (this.role === "ROLE_HR") {
            return 1;
        } else if (this.role === "ROLE_USER") {
            let userStore = useUserStore();
            const userId = window.localStorage.getItem('user_id');
            userStore.checkForExistingUserProfile(userId);
            if (!userStore.hasProfile) {
                return -1;
            } else {
                const profileId = String(useRoute().params.id);
                userStore.getProfileIdFromUser(userId);
                return String(userStore.profileId) === profileId ? 0 : -1;
            }
        } else {
            return -1;
        }
    }

    async enterEdit(): Promise<void> {
        await router.push({name: 'editView', params: {id: this.profileId}});
    }

    async lockProfile(): Promise<void> {
        const userStore = useUserStore();
        const userId = this.details.getOwnerId();
        await userStore.lockUser(userId);
        await this.loadLockStatusByUserId();
    }

    async deleteProfile(): Promise<void> {
        const id = this.profileId;
        const errorStore = useErrorStore();
        await axiosInstance.delete(`/api/v1/profiles/${id}`).then(() => {
            this.details = new DetailModel();
        }).catch((error) => {
            errorStore.catchDeleteError(error, id);
        });
        const detailStore = useDetailStore();
        detailStore.details = new DetailModel();
        await router.push(`/`);
    }
}
