import {useUserStore} from "@/stores/UserStore";

export function isAdmin(): boolean {
    return window.localStorage.getItem('role') === 'ROLE_ADMIN';
}

export function getProfileId(): String {
    if (useUserStore().hasProfile) {
        const userStore = useUserStore();
        const userId = window.localStorage.getItem('user_id');
        userStore.getProfileIdFromUser(userId);
        return userStore.profileId.toString();
    }
}

export function hasProfile(): boolean {
    if (window.localStorage.getItem('user_id')) {
        const userStore = useUserStore();
        const userId = window.localStorage.getItem('user_id');
        userStore.checkForExistingUserProfile(userId);
        return userStore.hasProfile;
    }
   return false;
}
