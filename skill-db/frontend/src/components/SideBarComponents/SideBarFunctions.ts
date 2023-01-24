import {useUserStore} from "@/stores/UserStore";

export function isAdmin(): boolean {
    return window.localStorage.getItem('role') === 'ROLE_ADMIN';
}

export function getProfileId(): String {
    hasProfile(); //to force reload after id changed
    const userStore = useUserStore();
    const userId = window.localStorage.getItem('user_id');
    userStore.getProfileIdFromUser(userId);
    return userStore.profileId.toString();
}

export function hasProfile(): boolean {
    const userStore = useUserStore();
    const userId = window.localStorage.getItem('user_id');
    userStore.checkForExistingUserProfile(userId);
    return userStore.hasProfile;
}