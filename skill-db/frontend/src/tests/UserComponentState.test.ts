import {describe, it, expect} from "vitest";

import {createTestingPinia} from "@pinia/testing";
import {useUserStore} from "@/stores/UserStore";
import {ConvertToUserModel} from "@/models/UserModel";
import {ConvertToRoleModel} from "@/models/RoleModel";
import {useErrorStore} from "@/stores/ErrorStore";
import {UserComponentState} from "@/components/UserComponents/UserComponentState";
import axiosInstance from "@/axios";

describe('UserComponentState',() => {

    let state: UserComponentState;

    beforeEach(() => {
        state = new UserComponentState();
    });

    it('loadPendingRoleChanges() makes correct axios call', async () => {
        createTestingPinia();
        const spyAxios = vitest.spyOn(axiosInstance, 'get');

        await state.loadPendingRoleChanges();

        expect(spyAxios).toBeCalledTimes(1);
        expect(spyAxios).toBeCalledWith('/api/v1/users/pending/role');
    });

    it('loadPendingUserLocks() makes correct axios call', async () => {
        createTestingPinia();
        const spyAxios = vitest.spyOn(axiosInstance, 'get');

        await state.loadPendingUserLocks();

        expect(spyAxios).toBeCalledTimes(1);
        expect(spyAxios).toBeCalledWith('/api/v1/users/pending/lock');
    });

    it('locking an admin gives error', async () => {
        createTestingPinia();
        const errStore = useErrorStore();
        const userStore = useUserStore();
        const spyPendingLocks = vitest.spyOn(state, 'loadPendingUserLocks')

        userStore.lockUser = vitest.fn(async () => {
            useErrorStore().hasError = true;
        });
        const user = ConvertToUserModel.toUserModel({
            locked: false,
            role: ConvertToRoleModel.toRoleModel({
                displayName: 'Admin',
                identifier: 'ADMIN'
            })
        });
        expect(user.getLocked()).toBeFalsy();

        await state.toggleLock(user);

        expect(errStore.hasError).toBeTruthy();
        expect(user.getLocked()).toBeFalsy();
        expect(spyPendingLocks).toHaveBeenCalledTimes(1);
    });

    it('not locked user, who is no admin, gets locked', async () => {
        createTestingPinia();
        const errStore = useErrorStore();
        const userStore = useUserStore();

        userStore.lockUser = vitest.fn(async () => {
            useErrorStore().hasError = false;
        });
        const user = ConvertToUserModel.toUserModel({
            locked: false,
            role: ConvertToRoleModel.toRoleModel({
                displayName: 'Nutzer',
                identifier: 'USER'
            })
        });
        expect(user.getLocked()).toBeFalsy();

        await state.toggleLock(user);

        expect(errStore.hasError).toBeFalsy();
        expect(user.getLocked()).toBeTruthy();
    });

    it('locked user, who is no admin, gets locked', async () => {
        createTestingPinia();
        const errStore = useErrorStore();
        const userStore = useUserStore();

        userStore.lockUser = vitest.fn(async () => {
            useErrorStore().hasError = false;
        });
        const user = ConvertToUserModel.toUserModel({
            locked: true,
            role: ConvertToRoleModel.toRoleModel({
                displayName: 'Nutzer',
                identifier: 'USER'
            })
        });
        expect(user.getLocked()).toBeTruthy();

        await state.toggleLock(user);

        expect(errStore.hasError).toBeFalsy();
        expect(user.getLocked()).toBeFalsy();
    });
});