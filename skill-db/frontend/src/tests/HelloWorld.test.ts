import {describe, it, expect} from "vitest";

import {mount} from "@vue/test-utils";
import leaveButton from "@/components/EditComponents/LeaveButton.vue";
import UploadImageButton from "@/components/EditComponents/UploadImageButton.vue";
import LeaveButton from "@/components/EditComponents/LeaveButton.vue";
describe('LeaveButton',() =>{
    it('LoginRenders', () =>{
        const wrapper = mount(leaveButton);
        expect(wrapper.exists()).toEqual(true);
        expect(wrapper.text()).toContain('Abbrechen');
    });
    it ('snap shot matches', () => {
        const wrapper = mount(LeaveButton);
        expect(wrapper).toMatchSnapshot()
    });
})

