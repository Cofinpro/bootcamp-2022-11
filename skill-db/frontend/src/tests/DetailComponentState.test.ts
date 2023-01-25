import {describe, it, expect} from "vitest";

import {createTestingPinia} from "@pinia/testing";
import {useUserStore} from "@/stores/UserStore";
import {DetailComponentState} from "@/components/DetailComponents/DetailComponentState";
import axiosInstance from "@/axios";

vi.mock('vue-router', async () => ({
    ...await vi.importActual('vue-router'),
    useRoute: vitest.fn(() => ({
        params: {
            id: 1
        }
    })),
}));

describe('DetailComponentState',() => {

    let state: DetailComponentState;

    beforeEach( () => {
        state = new DetailComponentState();
    });

    it('loadDetailsById() makes correct axios call', async () => {
        createTestingPinia();
        const id = '1';
        const spyAxios = vitest.spyOn(axiosInstance, 'get');

        await state.loadDetailsById(id);

        expect(spyAxios).toBeCalledTimes(1);
        expect(spyAxios).toBeCalledWith('/api/v1/profiles/1');

    });

    it('loadLockStatusByUserId() works for admin', async () => {
        const spyAxios = vitest.spyOn(axiosInstance, 'get');
        state.role = "ROLE_ADMIN";
        state.details.setOwnerId('1');

        await state.loadLockStatusByUserId();

        expect(spyAxios).toBeCalledTimes(1);
        expect(spyAxios).toBeCalledWith('/api/v1/users/1/locked');
    });

    it('loadLockStatusByUserId() works only for admin', async () => {
        const spyAxios = vitest.spyOn(axiosInstance, 'get');
        state.role = "ROLE_USER";

        await state.loadLockStatusByUserId();
        expect(spyAxios).not.toBeCalled();

        state.role = "ROLE_HR";
        await state.loadLockStatusByUserId();
        expect(spyAxios).not.toBeCalled();
    });

    it('auth() returns correct number', () => {
        createTestingPinia();
        const userStore = useUserStore();
        const spyCheckForExisting = vitest.spyOn(userStore, 'checkForExistingUserProfile');
        const spyProfileId = vitest.spyOn(userStore, 'getProfileIdFromUser');

        state.role = "ROLE_ADMIN";
        expect(state.auth()).toBe(2);

        state.role = "ROLE_HR";
        expect(state.auth()).toBe(1);

        state.role = "ROLE_USER";
        expect(state.auth()).toBe(-1);
        expect(spyCheckForExisting).toBeCalledTimes(1);
        expect(spyProfileId).not.toBeCalled();

        userStore.checkForExistingUserProfile = vitest.fn(() => {
            userStore.hasProfile = true;});
        userStore.getProfileIdFromUser = vitest.fn(() => {
            userStore.profileId = '1';});
        expect(state.auth()).toBe(0);

        userStore.getProfileIdFromUser = vitest.fn(() => {
            userStore.profileId = '0';});
        expect(state.auth()).toBe(-1);
    });

    it('not locked user is locked', async () => {
        state.ownerOfProfileIsLocked = true;
        const spyLoadUser = vitest.spyOn(state, 'loadLockStatusByUserId');

        await state.lockProfile();

        expect(spyLoadUser).toBeCalledTimes(1);
    });

    it('locked user is still locked', async () => {
        state.loadLockStatusByUserId = vitest.fn(async () => {
            state.ownerOfProfileIsLocked = true;
        });
        const spyLoadUser = vitest.spyOn(state, 'loadLockStatusByUserId');

        await state.lockProfile();

        expect(spyLoadUser).toBeCalledTimes(1);
    });
});