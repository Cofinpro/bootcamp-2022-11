
import {mount} from "@vue/test-utils";
import leaveButton from "@/components/EditComponents/LeaveButton.vue";
import ConfirmButton from "@/components/EditComponents/ConfirmButton.vue";
import vuetify from "@/plugins/vuetify";
import skillInput from "@/components/EditComponents/SkillInput.vue";
import {createTestingPinia} from "@pinia/testing";
import {expect, describe, it, vi} from "vitest";
import InputBlock from "@/components/EditComponents/inputBlock.vue";
import {checkDateFormat, checkPhoneNumberFormat} from "@/components/EditComponents/ValidationService";

describe('Inputblock', () => {

    it('validation of phone number works',  () =>{
        expect(checkPhoneNumberFormat('a11111111111')).toBeFalsy();
        expect(checkPhoneNumberFormat('111111111111111')).toBeFalsy();
        expect(checkPhoneNumberFormat('')).toBeFalsy();
        expect(checkPhoneNumberFormat('123456789012')).toBeTruthy();
    });

    it('validation of date works', () => {
        expect(checkDateFormat("12.13.1999")).toBeFalsy();
        expect(checkDateFormat('32.01.1999')).toBeFalsy();
        expect(checkDateFormat('30.01.199')).toBeFalsy();
        expect(checkDateFormat('30.01.1999')).toBeTruthy();
    });
});
