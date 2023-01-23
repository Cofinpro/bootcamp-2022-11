import {describe, it, vi, expect} from "vitest";

import {mount} from "@vue/test-utils";
import vuetify from "@/plugins/vuetify";
import uploadImageButton from "@/components/EditComponents/UploadImageButton.vue";
import {createTestingPinia} from "@pinia/testing";

describe('UploadImageButton', () => {
    it('Open File dialog without old image existing and renders correctly', () => {
        let openFileDialogSpy = vi.spyOn(uploadImageButton.methods, "openFileDialog");
        const wrapper = mount(uploadImageButton,
            {
                global: {
                    plugins: [vuetify, createTestingPinia()]
                }
            });
        //Test if file file dialog is opened
        let input = wrapper.find('input');
        expect(input.exists()).toBeTruthy();
        let dummyImage = wrapper.find('.image');
        expect(dummyImage.exists()).toBeTruthy();
        let uploadButton = wrapper.find('.v-btn');
        expect(uploadButton.exists()).toBeTruthy();
        uploadButton.trigger('click');
        expect(openFileDialogSpy).toHaveBeenCalledOnce();
    });

    it('Call method convert on file input change event', () => {
        let convertImageAndEmitToParentSpy = vi.spyOn(uploadImageButton.methods, "convertImageAndEmitToParent");
        const wrapper = mount(uploadImageButton,
            {
                global: {
                    plugins: [vuetify, createTestingPinia()]
                }
            });
        let input = wrapper.find('input');
        expect(input.exists()).toBeTruthy();
        input.trigger('input');
        expect(convertImageAndEmitToParentSpy).toHaveBeenCalledOnce();
    });

    it('test delete profile pic method being called', () => {
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
        let buttons = wrapper.findAll('.v-btn');
        expect(buttons.length).toBe(2);
        buttons[0].trigger('click.prevent'); //deletebutton
        expect(wrapper.vm.oldPic).toEqual('');
        expect(wrapper.emitted('upload')).toBeTruthy();
    });
});
