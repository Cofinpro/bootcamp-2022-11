import router from "@/router";
import {useUserStore} from "@/stores/UserStore";
import {useDetailStore} from "@/stores/DetailStore";
import {useRoute} from "vue-router";

export function auth(): number {
    const role = window.localStorage.getItem('role');
    if (role === "ROLE_ADMIN") {
        return 2;
    }
    if (role === "ROLE_HR") {
        return 1;
    } else if (role === "ROLE_USER") {
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

export function enterEdit(id: string): void {
    router.push({name: 'editView', params: {id: id}});
}

export async function lockProfile(id: string): Promise<void> {
    const detailStore = useDetailStore();
    await detailStore.loadDetailsById(id);
    const userId = detailStore.details.getOwnerId();

    const userStore = useUserStore();
    await userStore.loadUserById(userId);
    if (!userStore.user.getLocked()) {
        await userStore.lockUser(userId);
    }
}

export async function deleteProfile(id: string): void {
    const detailStore = useDetailStore();
    await detailStore.deleteDetailsByID(id);
    router.push(`/`);
}