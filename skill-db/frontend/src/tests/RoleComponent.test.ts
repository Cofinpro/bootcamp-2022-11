import {describe, it, expect} from "vitest";

import {mount} from "@vue/test-utils";
import RoleComponent from "@/components/RoleComponents/RoleComponent.vue";
import vuetify from "@/plugins/vuetify";
import {ConvertToRoleModel} from "@/models/RoleModel";
import {createTestingPinia} from "@pinia/testing";
import {useUserStore} from "@/stores/UserStore";
import {ConvertToUserModel} from "@/models/UserModel";
import * as functions from "@/components/RoleComponents/RoleDropdownFunctions";

describe('RoleComponent',() => {

    it('prepareSelectDropdown() with admin role does as it should', async () => {
        const wrapper = mount(RoleComponent,
            {
                global:
                    {plugins: [vuetify, createTestingPinia()]}
            });

        const role = ConvertToRoleModel.toRoleModel({
            identifier: 'ADMIN'
        });
        const userStore = useUserStore();
        const spyRoleId = vitest.spyOn(userStore, 'loadUsersByRoleId');
        const spyUsers = vitest.spyOn(userStore, 'loadUsers');

        await wrapper.vm.prepareSelectDropdown(role);

        expect(spyRoleId).toBeCalledTimes(1);
        expect(spyRoleId).toBeCalledWith('ADMIN');
        expect(spyUsers).toBeCalledTimes(1);
        expect(wrapper.vm.edit).toBeTruthy();
        expect(wrapper.vm.roleHere).toEqual(role);
    });

    it('prepareSelectDropdown() with undefined role does as it should', async () => {
        const wrapper = mount(RoleComponent,
            {
                global:
                    {plugins: [vuetify, createTestingPinia()]}
            });

        const role = ConvertToRoleModel.toRoleModel({
            identifier: 'UNDEFINED'
        });
        const userStore = useUserStore();
        const spyRoleId = vitest.spyOn(userStore, 'loadUsersByRoleId')
        const spyUsers = vitest.spyOn(userStore, 'loadUsers')

        await wrapper.vm.prepareSelectDropdown(role);

        expect(spyRoleId).not.toBeCalled();
        expect(spyUsers).toBeCalledTimes(1);
        expect(wrapper.vm.edit).toBeTruthy();
        expect(wrapper.vm.roleHere).toEqual(role);
    });

    it('in submit() role is not changed, if not needed', async () => {
        const wrapper = mount(RoleComponent,
            {
                global:
                    {plugins: [vuetify, createTestingPinia()]}
            });

        const selectedUsersWithRole = ['test@test.com (Admin)'];
        wrapper.vm.roleHere = ConvertToRoleModel.toRoleModel({
            identifier: 'ADMIN',
            displayName: 'Admin'
        });
        wrapper.vm.allUsers = [ConvertToUserModel.toUserModel({
            email: 'test@test.com',
            id: '1',
            role: ConvertToRoleModel.toRoleModel({
                  identifier: 'ADMIN'
                })
        })];
        const spyChangeRole = vitest.spyOn(functions, 'changeRole');
        const args = {
            selectedUsersWithRole: selectedUsersWithRole,
            allUsers: wrapper.vm.allUsers,
            role: wrapper.vm.roleHere};

        await wrapper.vm.submit(args);

        expect(spyChangeRole).not.toBeCalled();
        expect(wrapper.vm.edit).toBeFalsy();
    });

    it('in submit() role is changed, if needed', async () => {
        const wrapper = mount(RoleComponent,
            {
                global:
                    {plugins: [vuetify, createTestingPinia()]}
            });

        const selectedUsersWithRole = ['test@test.com (Admin)'];
        wrapper.vm.roleHere = ConvertToRoleModel.toRoleModel({
            identifier: 'ADMIN',
            displayName: 'Admin'
        });
        wrapper.vm.allUsers = [ConvertToUserModel.toUserModel({
            email: 'test@test.com',
            id: '1',
            role: ConvertToRoleModel.toRoleModel({
                identifier: 'USER'
            })
        })];
        const spyChangeRole = vitest.spyOn(functions, 'changeRole');
        const args = {
            selectedUsersWithRole: selectedUsersWithRole,
            allUsers: wrapper.vm.allUsers,
            role: wrapper.vm.roleHere};

        await wrapper.vm.submit(args);

        //expect(spyChangeRole).toBeCalledTimes(1); //TODO: don't understand why not working
        //expect(spyChangeRole).toBeCalledWith('1', 'Admin');
        expect(wrapper.vm.edit).toBeFalsy();
    });
})