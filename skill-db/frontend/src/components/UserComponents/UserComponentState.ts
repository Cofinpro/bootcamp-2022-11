import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";
import {ConvertToOperationsModel, OperationsModel} from "@/models/OperationsModel";
import type {UserModel} from "@/models/UserModel";
import {useUserStore} from "@/stores/UserStore";

export class UserComponentState {

    roleChangeOperations: OperationsModel[];
    lockUserOperations: OperationsModel[];

    constructor() {
        this.roleChangeOperations = [];
        this.lockUserOperations = [];
    }

    async loadPendingRoleChanges(): Promise<void> {
        const errorStore = useErrorStore();
        await axiosInstance.get('/api/v1/users/pending/role').then((response) => {
            if (this.roleChangeOperations.length > 0) {
                this.roleChangeOperations = [];
            }
            response.data.forEach((element: object) => {
                this.roleChangeOperations.push(ConvertToOperationsModel.toOperationsModel(element));
            });
        }).catch((error) => {
            errorStore.catchGetAllError(error);
        });
    }

    async loadPendingUserLocks(): Promise<void> {
        const errorStore = useErrorStore();
        await axiosInstance.get('/api/v1/users/pending/lock').then((response) => {
            if (this.lockUserOperations.length > 0) {
                this.lockUserOperations = [];
            }
            response.data.forEach((element: object) => {
                this.lockUserOperations.push(ConvertToOperationsModel.toOperationsModel(element));
            });
        }).catch((error) => {
            errorStore.catchGetAllError(error);
        });
    }

    async toggleLock(user: UserModel): Promise<void> {
        const userStore = useUserStore();
        const errorStore = useErrorStore();
        await userStore.lockUser(user.getId());
        if (!errorStore.hasError) {
            user.setLocked(!user.getLocked());
        }
        await this.loadPendingUserLocks();
    }
}