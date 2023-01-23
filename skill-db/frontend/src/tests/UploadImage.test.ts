import {describe, it, expect} from "vitest";

import {mount} from "@vue/test-utils";
import vuetify from "@/plugins/vuetify";
import uploadImageButton from "@/components/EditComponents/UploadImageButton.vue";
import {createTestingPinia} from "@pinia/testing";

describe('UploadImageButton', () => {
    it('test mounting with old image and deleteProfilePic function', async () => {
        const wrapper = mount(uploadImageButton,
            {
                props: {
                    oldPicture: 'true'
                },
                global: {
                    plugins: [vuetify, createTestingPinia()],
                }
            });
        let oldImage = wrapper.find(".image");
        expect(oldImage.html()).toContain('true');
        wrapper.vm.deleteProfilePicture();
        expect(wrapper.vm.oldPic).toEqual('');
        expect(wrapper.emitted('toggleDelete')).toBeTruthy();
        expect(wrapper.emitted('upload')).toBeTruthy();
    });
});
