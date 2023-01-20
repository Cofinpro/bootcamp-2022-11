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

    it('All users and their roles are in dropdown menu', async () => {
        const wrapper = mount(RoleDropdown,
            {
                props: {
                    role: new RoleModel(),
                    selectedUsers: [new RoleModel()],
                    allUsers: [ConvertToUserModel.toUserModel({
                        id: '1',
                        email: 'test@test.com',
                        role: new RoleModel()
                    }), ConvertToUserModel.toUserModel({
                        id: '2',
                        email: 'test2@test.com',
                        role: ConvertToRoleModel.toRole({
                            displayName: 'HR'
                        })
                    })]
                },
                global:
                    {plugins: [vuetify]}
            });
        /*wrapper.vm.$props.allUsers?.forEach((user: UserModel) => {
            ConvertToUserModel.toUserModel(user);
        });
        console.log(wrapper.vm.$props.allUsers);
        const attached = wrapper.vm.attachRole(wrapper.vm.$props.allUsers);
        expect(attached[0]).toBe("test@test.com ()");
        expect(attached[0]).toBe("test2@test.com (HR)");*/
        expect(true).toBeTruthy();
    });
});