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
    id: string;
    role: string;

    constructor() {
        this.details = new DetailModel();
        this.profilePic = '';
        this.id = useRoute().params.id.toString();
        this.role = window.localStorage.getItem('role');
    }

    async loadDetailsById(id: string): Promise<void> {
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

    auth(): number {
        if (this.role === "ROLE_ADMIN") {
            return 2;
        }
        if (this.role === "ROLE_HR") {
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
        router.push({name: 'editView', params: {id: this.id}});
    }

    async lockProfile(): Promise<void> {
        const userId = this.details.getOwnerId();

        const userStore = useUserStore();
        await userStore.loadUserById(userId);
        if (!userStore.user.getLocked()) {
            await userStore.lockUser(userId);
        }
    }

    async deleteProfile(): Promise<void> {
        const detailStore = useDetailStore();
        await detailStore.deleteDetailsByID(this.id);
        router.push(`/`);
    }
}