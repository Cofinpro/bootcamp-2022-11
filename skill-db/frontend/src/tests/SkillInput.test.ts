
import {mount} from "@vue/test-utils";
import leaveButton from "@/components/EditComponents/LeaveButton.vue";
import ConfirmButton from "@/components/EditComponents/ConfirmButton.vue";
import vuetify from "@/plugins/vuetify";
import {describe, it, expect} from "vitest";
import skillInput from "@/components/EditComponents/SkillInput.vue";
import {createTestingPinia} from "@pinia/testing";

describe('SkillInput', () => {
    it('SkillInput Renders', () => {
        const wrapper = mount(skillInput,
            {
                props:{
                    skillsIn: ['a','b']
                },
                global: {
                     plugins: [vuetify, createTestingPinia()]
                }
            });
        expect(wrapper.text()).toContain('Skill nicht gefunden?')

    });
});
