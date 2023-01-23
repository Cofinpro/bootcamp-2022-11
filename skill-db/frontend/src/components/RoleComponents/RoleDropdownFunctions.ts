import type {RoleModel} from "@/models/RoleModel";
import type {UserModel} from "@/models/UserModel";
import {useUserStore} from "@/stores/UserStore";

export function isDefined(role: RoleModel): boolean {
    return role.getIdentifier() !== 'UNDEFINED';
}

export async function useSubmit(args: any): Promise<void> {
    const selectedUsersWithRole: string[] = args.selectedUsersWithRole;
    const allUsers: UserModel[] = args.allUsers;
    const role: RoleModel = args.role;
    const userStore = useUserStore();
    for (const selected of selectedUsersWithRole) {
        for (const user of allUsers) {
            if(user.getEmail() === selected.split("(")[0].trim()
                && user.getRole().getIdentifier() !== role.getIdentifier()) {
                await userStore.changeRole(user.getId(), role.getDisplayName());
            }
        }
    }
}