import type {RoleModel} from "@/models/RoleModel";
import type {UserModel} from "@/models/UserModel";
import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";
import {AxiosError} from "axios";

export function isDefined(role: RoleModel): boolean {
    return role.getIdentifier() !== 'UNDEFINED';
}

export async function useSubmit(args: any): Promise<void> {
    const selectedUsersWithRole: string[] = args.selectedUsersWithRole;
    const allUsers: UserModel[] = args.allUsers;
    const role: RoleModel = args.role;
    for (const selected of selectedUsersWithRole) {
        for (const user of allUsers) {
            if(user.getEmail() === selected.split("(")[0].trim()
                && user.getRole().getIdentifier() !== role.getIdentifier()) {
                await changeRole(user.getId(), role.getDisplayName());
            }
        }
    }
}

async function changeRole(id: string, newRole: string): Promise<void> {
    const errorStore = useErrorStore()
    await axiosInstance.patch(`/api/v1/users/${id}/${newRole}`).then((response) => {
        if (response.status === 202) {
            throw new AxiosError(response.data.message, String(response.status));
        } else {
            errorStore.toggleHasError();
        }
    }).catch((error) => {
        errorStore.catchPostPatchError(error);
    });
}