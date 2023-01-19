
import {mount} from "@vue/test-utils";
import leaveButton from "@/components/EditComponents/LeaveButton.vue";
import ConfirmButton from "@/components/EditComponents/ConfirmButton.vue";
import vuetify from "@/plugins/vuetify";
import skillInput from "@/components/EditComponents/SkillInput.vue";
import {createTestingPinia} from "@pinia/testing";

describe('SkillInput', () => {
    it('SkillInput Renders', async  () => {
        const wrapper = mount(skillInput,
            {
                props:{
                    skillsIn: ['a','b']
                },
                global: {
                     plugins: [vuetify, createTestingPinia()]
                }
            });
        expect(wrapper.text()).toContain('Skill nicht gefunden?');
        let button = wrapper.findComponent('.v-btn');
        expect(button.exists()).toBeTruthy();
        const ax = await button.trigger('click');
        button = wrapper.findComponent('.v-btn');
        expect(button.exists()).toBeFalsy();
    });
});
