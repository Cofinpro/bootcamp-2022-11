import {describe, it, expect} from "vitest";

import {mount} from "@vue/test-utils";
import AlertWithTooltip from "@/components/UserComponents/AlertWithTooltip.vue";
import vuetify from "@/plugins/vuetify";
import {UserModel} from "@/models/UserModel";
import {ConvertToOperationsModel} from "@/models/OperationsModel";

describe('AlertWithTooltip',() =>{
    it('Icon is showing when target equals userId', async () =>{
        const wrapper = mount(AlertWithTooltip,
            {
                props:{
                    user: new UserModel(),
                    operationType: "lockUser",
                    operations: [ConvertToOperationsModel.toOperationsModel({
                        target: "",
                        initiator: "initiator",
                        roleId: ""
                    })]
                },
                global:
                    { plugins: [vuetify] }
            });
        const icon = wrapper.findComponent('.v-icon');
        expect(icon.exists()).toBeTruthy();

        //How to trigger hover and appearing of <span>?
        /*await icon.trigger('mouseover');
        expect(wrapper.findComponent('#test_span').exists()).toBeTruthy();*/
    });

    it('Icon does not exist when target unequals userId', async () =>{
        const wrapper = mount(AlertWithTooltip,
            {
                props:{
                    user: new UserModel(),
                    operationType: "changeRole",
                    operations: [ConvertToOperationsModel.toOperationsModel({
                        target: "target",
                        initiator: "initiator",
                        roleId: ""
                    })]
                },
                global:
                    { plugins: [vuetify] }
            });
        const icon = wrapper.find('.v-icon');
        expect(icon.exists()).toBeFalsy();
    });
})