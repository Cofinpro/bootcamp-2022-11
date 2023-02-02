import {ConvertToRoleModel, RoleModel} from "@/models/RoleModel";
import type {UserModel} from "@/models/UserModel";
import {useUserStore} from "@/stores/UserStore";
import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";
import {AxiosError} from "axios";
import {ConvertToUserModel} from "@/models/UserModel";

export class RoleComponentState {
    edit: boolean;
    allRoles: RoleModel[];
    selectedUsers: UserModel[];
    selectedUsersWithRole: string[];
    allUsers: UserModel[];
    allUsersWithRole: string[];
    role: RoleModel;


    constructor() {
        this.edit = false;
        this.allRoles = [];
        this.selectedUsers = [];
        this.selectedUsersWithRole = [];
        this.allUsers = [];
        this.allUsersWithRole = [];
        this.role = new RoleModel();
    }

    async loadAllRoles(): Promise<void> {
        const errorStore = useErrorStore();
        await axiosInstance.get(`/api/v1/roles`).then((response) => {
            if(this.allRoles.length > 0) {
                this.allRoles = [];
            }
            response.data.forEach((element: object) => {
                const role: RoleModel = ConvertToRoleModel.toRoleModel(element);
                this.allRoles.push(role)
            });
        }).catch((error) => {
            errorStore.catchAllAxiosErrors(error, 'GetAll', '');
        });
    }

    attachRole() {
        this.selectedUsersWithRole = [];
        this.selectedUsers.forEach((user: UserModel) => {
            this.selectedUsersWithRole.push(`${user.getEmail()} (${user.getRole().getDisplayName()})`)
        });
        this.allUsersWithRole = [];
        this.allUsers.forEach((user: UserModel) => {
            this.allUsersWithRole.push(`${user.getEmail()} (${user.getRole().getDisplayName()})`)
        });
    }

    async prepareSelectDropdown(role: RoleModel): Promise<void> {
        this.selectedUsers = [] as UserModel[];
        if (role.isDefined()) {
            await this.loadUsersWithThisRole(role);
        }
        const userStore = useUserStore();
        await userStore.loadUsers();
        this.allUsers = userStore.users as UserModel[];

        this.edit = true;
        this.role = role;
    }

    async loadUsersWithThisRole(role: RoleModel): Promise<void> {
        const id = role.getIdentifier();

        const errorStore = useErrorStore();
        await axiosInstance.get(`/api/v1/roles/${id}/users`).then((response) => {
            if (this.selectedUsers.length > 0) {
                this.selectedUsers = [];
            }
            response.data.forEach((element: object) => {
                this.selectedUsers.push(ConvertToUserModel.toUserModel(element));
            });
        }).catch((error) => {
            errorStore.catchAllAxiosErrors(error, 'GetRoleId', id);
        });
    }

    async submit(): Promise<void> {
        for (const selected of this.selectedUsersWithRole) {
            for (const user of this.allUsers) {
                if(user.getEmail() === selected.split("(")[0].trim()
                    && user.getRole().getIdentifier() !== this.role.getIdentifier()) {
                    await this.changeRole(user.getId(), this.role.getDisplayName());
                }
            }
        }
        this.edit = false;
    }

    async changeRole(id: string, newRole: string): Promise<void> {
        const errorStore = useErrorStore()
        await axiosInstance.patch(`/api/v1/users/${id}/${newRole}`).then((response) => {
            if (response.status === 202) {
                const error = new AxiosError(response.data.message, String(response.status));
                errorStore.catchCustomAxios(error);
            } else {
                errorStore.toggleHasError();
            }
        }).catch((error) => {
            errorStore.catchAllAxiosErrors(error, 'PostPatch', '');
        });
    }
}