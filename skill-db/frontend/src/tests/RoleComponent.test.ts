import {describe, it, expect} from "vitest";

import {mount} from "@vue/test-utils";
import RoleComponent from "@/components/RoleComponents/RoleComponent.vue";
import vuetify from "@/plugins/vuetify";
import {ConvertToRoleModel} from "@/models/RoleModel";
import {ConvertToUserModel} from "@/models/UserModel";
import {RoleComponentState} from "@/components/RoleComponents/RoleComponentState";
import RoleDropdown from "@/components/RoleComponents/RoleDropdown.vue";

describe('RoleComponent',() => {

    let roleState: RoleComponentState;

    beforeEach(() => {
        roleState = new RoleComponentState();
        roleState.role = ConvertToRoleModel.toRoleModel({
            identifier: '',
            displayName: 'Admin',
            description: '',
            user: [],
            color: ''
        });
        roleState.selectedUsers = [ConvertToUserModel.toUserModel({
            role: ConvertToRoleModel.toRoleModel({
                displayName: 'HR'
            })
        })];
    });

    it('DisplayName of role is showing, button is emitting', async () => {
        const wrapper = mount(RoleDropdown,
            {
                props: {
                    state: roleState
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
})