import {describe, it, expect} from "vitest";

import {mount} from "@vue/test-utils";
import UserComponent from "@/components/UserComponent.vue";
import vuetify from "@/plugins/vuetify";
import {createTestingPinia} from "@pinia/testing";
import {useUserStore} from "@/stores/UserStore";
import {ConvertToUserModel} from "@/models/UserModel";
import {ConvertToRoleModel} from "@/models/RoleModel";
import {useErrorStore} from "@/stores/ErrorStore";

describe('UserComponent',() => {

    it('not locked user, who is no admin, gets locked', async () => {

        const wrapper = mount(UserComponent,
            {
                global:
                    {plugins: [vuetify, createTestingPinia()]}
            });

        const errStore = useErrorStore();
        const userStore = useUserStore();
        const spy = vitest.spyOn(userStore, 'loadPendingLockUsers')

        userStore.lockUser = vitest.fn(async () => {
            if(user.getRole().getIdentifier() === 'ADMIN') {
                useErrorStore().hasError = true;
            }
        });

        const user = ConvertToUserModel.toUserModel({
            role: ConvertToRoleModel.toRoleModel({
                displayName: 'Nutzer',
                identifier: 'USER'
            })
        });
        await wrapper.vm.toggleLock(user);
        expect(spy).toHaveBeenCalledTimes(1);
        expect(errStore.hasError).toBeFalsy();
        expect(user.getLocked()).toBeTruthy();
    });

    it('locked user, who is no admin, gets unlocked', async () => {

        const wrapper = mount(UserComponent,
            {
                global:
                    {plugins: [vuetify, createTestingPinia()]}
            });

        const errStore = useErrorStore();
        const userStore = useUserStore();
        userStore.lockUser = vitest.fn(async () => {
            if(user.getRole().getIdentifier() === 'ADMIN') {
                useErrorStore().hasError = true;
            }
        });

        const user = ConvertToUserModel.toUserModel({
            locked: true,
            role: ConvertToRoleModel.toRoleModel({
                displayName: 'Nutzer',
                identifier: 'User'
            })
        });
        await wrapper.vm.toggleLock(user);
        expect(errStore.hasError).toBeFalsy();
        expect(user.getLocked()).toBeFalsy();
    });

    it('not locked admin does not get locked', async () => {

        const wrapper = mount(UserComponent,
            {
                global:
                    {plugins: [vuetify, createTestingPinia()]}
            });

        const errStore = useErrorStore();
        const userStore = useUserStore();
        userStore.lockUser = vitest.fn(async () => {
            if(user.getRole().getIdentifier() === 'ADMIN') {
                useErrorStore().hasError = true;
            }
        });

        const user = ConvertToUserModel.toUserModel({
            role: ConvertToRoleModel.toRoleModel({
                displayName: 'Administrator',
                identifier: 'ADMIN'
            })
        });
        await wrapper.vm.toggleLock(user);
        expect(errStore.hasError).toBeTruthy();
        expect(user.getLocked()).toBeFalsy();
    });
});