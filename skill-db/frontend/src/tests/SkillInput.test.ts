
import {mount} from "@vue/test-utils";
import vuetify from "@/plugins/vuetify";
import skillInput from "@/components/EditComponents/SkillInput.vue";
import {createTestingPinia} from "@pinia/testing";
import {expect, describe, it} from "vitest";

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
        wrapper.vm.detailStore.skills = ['a','b'];
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
        wrapper.vm.detailStore.skills = ['a','b'];
        let button = wrapper.getComponent('.v-btn');
        await button.trigger('click');
        button = wrapper.find('.v-btn');
        expect(button.exists()).toBeFalsy();
        let inputfields = wrapper.findAll('input');
        expect(inputfields.length).toBe(2);
        wrapper.vm.newSkills = 'hallo'
        expect(wrapper.vm.newSkills).toEqual('hallo');
        await inputfields[1].trigger('keydown.enter');
        expect(wrapper.vm.newSkills).toEqual('');
        expect(wrapper.text()).toContain('hallo');
        expect(wrapper.emitted('update:skills')).toBeTruthy();
        expect(wrapper.vm.skills).toContain('hallo');
    });

    it('addskills function test', ()=>{
        const wrapper = mount(skillInput,
            {
                props:{
                    skillsIn: ['a','b']
                },
                global: {
                    plugins: [vuetify, createTestingPinia()]
                }
            });
        wrapper.vm.detailStore.skills = ['a','b'];
        wrapper.vm.skills=['x','y'];
        wrapper.vm.newSkills = 'c';
        wrapper.vm.addSkills();
        expect(wrapper.vm.skills).toContain('c');
        expect(wrapper.vm.skills).toContain('x');
        expect(wrapper.vm.skills).toContain('y');
        expect(wrapper.vm.detailStore.skills).toContain('c');
        expect(wrapper.vm.detailStore.skills).toContain('a');
        expect(wrapper.vm.detailStore.skills).toContain('b');
        expect(wrapper.vm.newSkills).toEqual('');
        expect(wrapper.vm.showAddSkills).toBeFalsy();
    })
});
