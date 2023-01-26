import {flushPromises, mount} from "@vue/test-utils";
import vuetify from "@/plugins/vuetify";
import skillInput from "@/components/EditComponents/SkillInput.vue";
import {createTestingPinia} from "@pinia/testing";
import {expect, describe, it} from "vitest";
import {defineComponent, h, Suspense} from "vue";
import SkillInput from "@/components/EditComponents/SkillInput.vue";
/*
const mountSuspense = async (options) => {
    const wrapper = mount(defineComponent({
        render() {
            return h(Suspense, options.props, {
                default: h(SkillInput),
                fallback: h('div', 'fallback')
            })
        }
    }), options)

    await flushPromises()
    return wrapper
}
describe('SkillInput', () => {
    it('SkillInput Renders correctly', async () => {
        const options = {
            props: {
                modelValue: ['a', 'b']
            },
            global: {
                plugins: [vuetify, createTestingPinia()]
            }
        }
        const wrapper = await mountSuspense(options);
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
                props: {
                    skillsIn: ['a', 'b']
                },
                global: {
                    plugins: [vuetify, createTestingPinia()]
                }
            });
        wrapper.vm.detailStore.skills = ['a', 'b'];
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
        expect(wrapper.vm.availableSkills).toContain('hallo');
    });

    it('addskills function test', () => {
        const wrapper = mount(skillInput,
            {
                props: {
                    modelValue: ['a', 'b']
                },
                global: {
                    plugins: [vuetify, createTestingPinia()]
                }
            });
        wrapper.vm.availableSkills = ['a', 'b'];
        wrapper.vm.modelValue = ['x', 'y'];
        wrapper.vm.newSkills = 'c';
        wrapper.vm.addSkills();
        expect(wrapper.vm.modelValue).toContain('c');
        expect(wrapper.vm.modelValue).toContain('x');
        expect(wrapper.vm.modelValue).toContain('y');
        expect(wrapper.vm.availableSkills).toContain('c');
        expect(wrapper.vm.availableSkills).toContain('a');
        expect(wrapper.vm.availableSkills).toContain('b');
        expect(wrapper.vm.newSkills).toEqual('');
        expect(wrapper.vm.showAddSkills).toBeFalsy();
    })
});
*/
