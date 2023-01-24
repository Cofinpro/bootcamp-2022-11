import {describe, it, expect} from "vitest";

import {mount} from "@vue/test-utils";
import leaveButton from "@/components/EditComponents/LeaveButton.vue";
import ConfirmButton from "@/components/EditComponents/ConfirmButton.vue";
import vuetify from "@/plugins/vuetify";

//Tests that button contains correct content and emits submit event.
describe('LeaveButton',() =>{
    it('LoginRenders', () =>{
        const wrapper = mount(leaveButton,
            {
                global:
                    { plugins: [vuetify] }
            });
        expect(wrapper.exists()).toEqual(true);
        expect(wrapper.text()).toContain('Abbrechen');
        const button = wrapper.getComponent('.v-btn');
        button.trigger('click');
        expect(wrapper.emitted('click')).toBeTruthy();
    });
})
//Tests that button contains correct content and emits submit event.
describe('ConfirmButton', () => {
    it('ConfirmTest', () => {
        const wrapper = mount(ConfirmButton, {
            props: {
                isValid: true,
                update: true,
            },
            global: {
                plugins: [vuetify]
            }
        });
        expect(wrapper.exists()).toEqual(true);
        expect(wrapper.text()).toContain('Änderungen speichern');
        const button = wrapper.getComponent('.v-btn');
        button.trigger('click');
        expect(wrapper.emitted('submit')).toBeTruthy();
        expect(button.element.disabled).toBe(false);
    });
});
