import {describe, it, expect} from "vitest";

import {ConvertToRoleModel, RoleModel} from "@/models/RoleModel";
import {ConvertToUserModel} from "@/models/UserModel";
import {RoleComponentState} from "@/components/RoleComponents/RoleComponentState";
import axiosInstance from "@/axios";
import {createTestingPinia} from "@pinia/testing";
import {useUserStore} from "@/stores/UserStore";

describe('RoleComponentState', () => {

    let roleState;

    beforeEach(() => {
        roleState = new RoleComponentState();
        roleState.role = ConvertToRoleModel.toRoleModel({
            identifier: 'ADMIN',
            displayName: 'Admin'
        });
        roleState.selectedUsers = [ConvertToUserModel.toUserModel({
            id: '0',
            email: 'test@test.com',
            role: ConvertToRoleModel.toRoleModel({
                identifier: 'HR',
                displayName: 'HR'
            })
        })];
        roleState.allUsers = [ConvertToUserModel.toUserModel({
            id: '1',
            email: 'test1@test.com',
            role: ConvertToRoleModel.toRoleModel({
                identifier: 'ADMIN',
                displayName: 'Admin'
            })
        }), ConvertToUserModel.toUserModel({
            id: '2',
            email: 'test2@test.com',
            role: ConvertToRoleModel.toRoleModel({
                identifier: 'HR',
                displayName: 'HR'
            })
        }), ConvertToUserModel.toUserModel({
            id: '3',
            email: 'test3@test.com',
            role: ConvertToRoleModel.toRoleModel({
                identifier: 'USER',
                displayName: 'Nutzer'
            })
        }), ConvertToUserModel.toUserModel({
            id: '4',
            email: 'test4@test.com',
            role: new RoleModel()
        })];
    });

    it('loadAllRoles() makes correct axios call', async () => {
        createTestingPinia();
        const spyAxios = vi.spyOn(axiosInstance, 'get');

        await roleState.loadAllRoles();

        expect(spyAxios).toBeCalledTimes(1);
        expect(spyAxios).toBeCalledWith('/api/v1/roles');
    });

    it('attachRole() attaches role', async () => {
        roleState.attachRole();

        expect(roleState.allUsersWithRole.length).toBe(4);
        expect(roleState.allUsersWithRole).toContain("test1@test.com (Admin)");
        expect(roleState.allUsersWithRole).toContain("test2@test.com (HR)");
        expect(roleState.allUsersWithRole).toContain("test3@test.com (Nutzer)");
        expect(roleState.allUsersWithRole).toContain("test4@test.com ()");
        expect(roleState.selectedUsersWithRole).toContain("test@test.com (HR)")
    });

    it('prepareSelectDropdown() with defined role works correctly', async () => {
        createTestingPinia();
        const spyUsersWithRole = vi.spyOn(roleState, 'loadUsersWithThisRole');
        const spyAllUsers = vi.spyOn(roleState, 'loadAllUsers');

        await roleState.prepareSelectDropdown(roleState.role);

        expect(spyUsersWithRole).toBeCalledWith(roleState.role);
        expect(spyAllUsers).toBeCalled();
        expect(roleState.edit).toBeTruthy();
        expect(roleState.role).toBe(roleState.role);
    });

    it('prepareSelectDropdown() with undefined role works correctly', async () => {
        createTestingPinia();
        const role = new RoleModel();
        const spyUsersWithRole = vi.spyOn(roleState, 'loadUsersWithThisRole');
        const spyAllUsers = vi.spyOn(roleState, 'loadAllUsers');

        await roleState.prepareSelectDropdown(role);

        expect(spyUsersWithRole).not.toBeCalled();
        expect(spyAllUsers).toBeCalled();
        expect(roleState.edit).toBeTruthy();
        expect(roleState.role).toBe(role);
    });

    it('loadUsersWithThisRole() calls userStore', async () => {
        createTestingPinia();
        const userStore = useUserStore();
        const spyUsersWithRole = vi.spyOn(userStore, 'loadUsersByRoleId');

        await roleState.loadUsersWithThisRole(roleState.role);

        expect(spyUsersWithRole).toBeCalledWith(roleState.role.getIdentifier());
    });

    it('loadAllUsers() calls userStore', async () => {
        createTestingPinia();
        const userStore = useUserStore();
        const spyAllUsers = vi.spyOn(userStore, 'loadUsers');

        await roleState.loadAllUsers(roleState.role);

        expect(spyAllUsers).toBeCalled();
    });

    it('in useSubmit() role is not changed, if not needed', async () => {
        roleState.selectedUsers = [roleState.allUsers[0]];
        roleState.attachRole();
        const spyChangeRole = vitest.spyOn(roleState, 'changeRole');

        await roleState.useSubmit();

        expect(spyChangeRole).not.toBeCalled();
        expect(roleState.edit).toBeFalsy();
    });

    it('in useSubmit() role is changed, if needed', async () => {
        roleState.selectedUsers = [roleState.allUsers[1]];
        roleState.attachRole();
        const spyChangeRole = vitest.spyOn(roleState, 'changeRole');

        await roleState.useSubmit();
        console.log(roleState.selectedUsers);

        expect(spyChangeRole).toBeCalledTimes(1);
        expect(spyChangeRole).toBeCalledWith('2', 'Admin');
        expect(roleState.edit).toBeFalsy();
    });

    it('changeRole() makes correct axios call', async () => {
        createTestingPinia();
        const spyAxios = vi.spyOn(axiosInstance, 'patch');

        await roleState.changeRole('0', 'Admin');

        expect(spyAxios).toBeCalledTimes(1);
        expect(spyAxios).toBeCalledWith('/api/v1/users/0/Admin');
    });

});