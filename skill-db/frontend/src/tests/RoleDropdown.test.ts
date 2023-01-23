import {describe, it, expect} from "vitest";

import {mount} from "@vue/test-utils";
import RoleDropdown from "@/components/RoleComponents/RoleDropdown.vue";
import vuetify from "@/plugins/vuetify";
import {ConvertToRoleModel, RoleModel} from "@/models/RoleModel";
import {ConvertToUserModel, UserModel} from "@/models/UserModel";


describe('RoleDropdown', () => {
    it('DisplayName of role is showing, button is emitting', async () => {
        const wrapper = mount(RoleDropdown,
            {
                props: {
                    role: ConvertToRoleModel.toRole({
                        identifier: '',
                        displayName: 'Admin',
                        description: '',
                        user: [],
                        color: ''
                    }),
                    selectedUsers: [ConvertToUserModel.toUserModel({
                        role: ConvertToRoleModel.toRole({
                            displayName: 'HR'
                        })
                    })],
                    allUsers: [new UserModel()]
                },
                global:
                    {plugins: [vuetify]}
            });

        expect(wrapper.text()).toContain("Admin"); //name of role
        expect(wrapper.text()).toContain("(HR)"); //role of selected user

        const button = wrapper.findComponent('.v-btn');
        await button.trigger('click');
        expect(wrapper.emitted('clicked')).toBeTruthy();
    });

    it('attachRole() attaches role', async () => {
        const wrapper = mount(RoleDropdown,
            {
                props: {
                    role: new RoleModel(),
                    selectedUsers: [new UserModel()],
                    allUsers: [ConvertToUserModel.toUserModel({
                        id: '1',
                        email: 'test1@test.com',
                        role: ConvertToRoleModel.toRole({
                            displayName: 'Admin'
                        })
                    }), ConvertToUserModel.toUserModel({
                        id: '2',
                        email: 'test2@test.com',
                        role: ConvertToRoleModel.toRole({
                            displayName: 'HR'
                        })
                    }), ConvertToUserModel.toUserModel({
                        id: '3',
                        email: 'test3@test.com',
                        role: ConvertToRoleModel.toRole({
                            displayName: 'Nutzer'
                        })
                    }), ConvertToUserModel.toUserModel({
                        id: '4',
                        email: 'test4@test.com',
                        role: new RoleModel()
                    })]
                },
                global:
                    {plugins: [vuetify]}
            });
        const attached = wrapper.vm.attachRole(wrapper.vm.$props.allUsers);

        expect(attached.length).toBe(4);
        expect(attached).toContain("test1@test.com (Admin)");
        expect(attached).toContain("test2@test.com (HR)");
        expect(attached).toContain("test3@test.com (Nutzer)");
        expect(attached).toContain("test4@test.com ()");
    });
});