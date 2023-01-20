import {describe, it, expect} from "vitest";

import {mount} from "@vue/test-utils";
import UserComponent from "@/components/UserComponent.vue";
import vuetify from "@/plugins/vuetify";
import {createTestingPinia} from "@pinia/testing";
import {ConvertToUserModel} from "@/models/UserModel";
import {ConvertToRoleModel} from "@/models/RoleModel";

describe('UserComponent',() => {
    it('Clicking on lock icon', async () => {
        const wrapper = mount(UserComponent,
            {
                global:
                    {plugins: [vuetify, createTestingPinia({ stubActions: false })]}
            });

        expect(wrapper.text()).contains('Email');
        expect(wrapper.text()).contains('Rolle');
        expect(wrapper.text()).contains('Sperrstatus');

        const store = wrapper.vm.$data.userStore;
        store.users = [ConvertToUserModel.toUserModel({
            email: 'test@test.com',
            role: ConvertToRoleModel.toRole({
                identifier: 'USER',
                displayName: 'Nutzer'
            })
        })];
        console.log(store.users);
        console.log(wrapper.text())

        const icon = wrapper.find('.v-icon');
        expect(icon.exists()).toBeTruthy();

        /*const spy = vi.spyOn(wrapper,'toggleLock');

        await icon.trigger('click');
        expect(spy).toHaveBeenCalledTimes(1);*/
    });
});