import router from "@/router";
import {useUserStore} from "@/stores/UserStore";
import {useDetailStore} from "@/stores/DetailStore";
import {useRoute} from "vue-router";
import {ConvertResponseToDetailModel, DetailModel} from "@/models/DetailModel";
import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";

export class DetailComponentState {

    details: DetailModel;
    profilePic: string;
    profileId: string;
    role: string;
    ownerOfProfileIsLocked: boolean;

    constructor() {
        this.details = new DetailModel();
        this.profilePic = '';
        this.profileId = useRoute().params.id.toString();
        this.role = window.localStorage.getItem('role');
        this.ownerOfProfileIsLocked = true;
    }

    async loadDetailsById(): Promise<void> {
        const id = this.profileId;
        let profilePicId = null;
        const errorStore = useErrorStore();
        await axiosInstance.get(`/api/v1/profiles/${id}`).then((response) => {
            this.details = ConvertResponseToDetailModel.toDetailModel(response.data);
            profilePicId = response.data.profilePicId;
        }).catch((error) => {
            errorStore.catchGetError(error, id);
        });
        await axiosInstance({
            url: `/api/v1/images/${profilePicId}`,
            method: 'get',
            responseType: 'blob'
        }).then((response) => {
            if (response.data.size === 0) {
                this.profilePic = '';
            } else {
                this.profilePic = URL.createObjectURL(response.data);
            }
        }).catch((error) => {
            console.log(error);
            errorStore.catchDownloadImageError(error);
        })
    }

    async setupLock(): Promise<void> {
        if (this.auth() > 1) {
            await this.loadLockStatusByUserId();
        }
    }

    async loadLockStatusByUserId(): Promise<void> {
        const id = this.details.getOwnerId();
        const errorStore = useErrorStore();
        await axiosInstance.get(`/api/v1/users/${id}/locked`).then(response => {
            this.ownerOfProfileIsLocked = Boolean(response.data);
        }).catch((error) => {
            errorStore.catchGetProfileError(error, id);
        })
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

    enterEdit(): void {
        const detailStore = useDetailStore();
        detailStore.details = this.details;
        router.push({name: 'editView', params: {id: this.profileId}});
    }

    async lockProfile(): Promise<void> {
        await this.loadLockStatusByUserId();
        const userStore = useUserStore();
        const userId = this.details.getOwnerId();
        await userStore.lockUser(userId);
        await this.loadLockStatusByUserId();
    }

    async deleteProfile(): Promise<void> {
        const detailStore = useDetailStore();
        await detailStore.deleteDetailsByID(this.profileId);
        router.push(`/`);
    }
}