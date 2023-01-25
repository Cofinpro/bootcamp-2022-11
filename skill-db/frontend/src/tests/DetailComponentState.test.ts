import {describe, it, expect} from "vitest";

import {createTestingPinia} from "@pinia/testing";
import {useUserStore} from "@/stores/UserStore";
import {ConvertToUserModel} from "@/models/UserModel";
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

    it('loadDetailsById() makes correct axios call', async () => {
        createTestingPinia();
        const id = '1';
        const spyAxios = vitest.spyOn(axiosInstance, 'get');
        const state = new DetailComponentState();

        await state.loadDetailsById(id);

        expect(spyAxios).toBeCalledTimes(1);
        expect(spyAxios).toBeCalledWith('/api/v1/profiles/1');

    });

    it('auth() returns correct number', () => {
        createTestingPinia();
        const userStore = useUserStore();
        const spyCheckForExisting = vitest.spyOn(userStore, 'checkForExistingUserProfile');
        const spyProfileId = vitest.spyOn(userStore, 'getProfileIdFromUser');
        const state = new DetailComponentState();

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
    })

    it('not locked user is locked', async () => {
        const userStore = useUserStore();
        userStore.loadUserById = vitest.fn(async () => {
            userStore.user = ConvertToUserModel.toUserModel({
                locked: false
            })
        });
        const state = new DetailComponentState();
        const spyLoadUser = vitest.spyOn(userStore, 'loadUserById');
        const spyLock = vitest.spyOn(userStore, 'lockUser');

        await state.lockProfile();

        expect(spyLoadUser).toBeCalledTimes(1);
        expect(spyLock).toBeCalledTimes(1);
        expect(spyLock).toBeCalledWith('');
    });

    it('locked user is still locked', async () => {
        createTestingPinia();
        const userStore = useUserStore();

        userStore.loadUserById = vitest.fn(async () => {
            userStore.user = ConvertToUserModel.toUserModel({
                locked: true
            })
        });
        const state = new DetailComponentState();
        const spyLoadUser = vitest.spyOn(userStore, 'loadUserById');
        const spyLock = vitest.spyOn(userStore, 'lockUser');

        await state.lockProfile();

        expect(spyLoadUser).toBeCalledTimes(1);
        expect(spyLock).not.toBeCalled();
    });
});