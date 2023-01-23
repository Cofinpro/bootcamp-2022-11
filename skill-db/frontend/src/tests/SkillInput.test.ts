
import {mount} from "@vue/test-utils";
import leaveButton from "@/components/EditComponents/LeaveButton.vue";
import ConfirmButton from "@/components/EditComponents/ConfirmButton.vue";
import vuetify from "@/plugins/vuetify";
import skillInput from "@/components/EditComponents/SkillInput.vue";
import {createTestingPinia} from "@pinia/testing";
import {expect, describe, it, vi} from "vitest";

describe('SkillInput', () => {
    it('SkillInput Renders correctly', async  () => {
        const wrapper = mount(skillInput,
            {
                props:{
                    skillsIn: ['a','b']
                },
                global: {
                     plugins: [vuetify, createTestingPinia()]
                }
            });
        const v_inputs = 'input';
        expect(wrapper.text()).toContain('Skill nicht gefunden?');
        let button = wrapper.findComponent('.v-btn');
        let inputfields = wrapper.findAll(v_inputs)
        expect(inputfields.length).toBe(1);
        expect(button.exists()).toBeTruthy();

    });
    it('Button clicked', async () => {
        const wrapper = mount(skillInput,
            {
                props:{
                    skillsIn: ['a','b']
                },
                global: {
                    plugins: [vuetify, createTestingPinia()]
                }
            });
        let button = wrapper.getComponent('.v-btn');
        await button.trigger('click');
        button = wrapper.find('.v-btn');
        expect(button.exists()).toBeFalsy();
        let inputfields = wrapper.findAll('input');
        expect(inputfields.length).toBe(2);
        await wrapper.setData({newSkills: 'hallo'});
        expect(wrapper.vm.newSkills).toEqual('hallo');
        await inputfields[1].trigger('keydown.enter');
        expect(wrapper.vm.newSkills).toEqual('');
        expect(wrapper.text()).toContain('hallo');
        expect(wrapper.emitted('update:skills')).toBeTruthy();
        expect(wrapper.vm.skills).toContain('hallo');
    });
});
