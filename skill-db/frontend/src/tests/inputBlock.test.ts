
import {mount} from "@vue/test-utils";
import leaveButton from "@/components/EditComponents/LeaveButton.vue";
import ConfirmButton from "@/components/EditComponents/ConfirmButton.vue";
import vuetify from "@/plugins/vuetify";
import skillInput from "@/components/EditComponents/SkillInput.vue";
import {createTestingPinia} from "@pinia/testing";
import {expect, describe, it, vi} from "vitest";
import InputBlock from "@/components/EditComponents/inputBlock.vue";

describe('Inputblock', () => {
    const firstName = 'Markus';
    const lastName = 'Kremer';
    const jobTitle = 'Consultant';
    const primarySkill = 'Technologie';
    const phoneNumber = '123123123123';
    const birthDate = '12.12.1999';
    it ('renders correctly', () => {
        const wrapper = mount(InputBlock, {
            props: {
                firstNameIn: firstName,
                lastNameIn: lastName,
                jobTitleIn: jobTitle,
                primarySkillIn: primarySkill,
                phoneNumberIn: phoneNumber,
                dateIn: birthDate
            },
            global: {
                plugins: [vuetify, createTestingPinia()]
            }
        });

        //test firstname field
        let firstNameField = wrapper.find('.test_firstName');
        expect(firstNameField.exists()).toBeTruthy();
        expect(firstNameField.html()).toContain('Vorname');

        //test lastName field
        let lastNameField = wrapper.find('.test_lastName');
        expect(lastNameField.exists()).toBeTruthy();
        expect(lastNameField.html()).toContain('Nachname');

        let jobTitleField = wrapper.find('.test_jobTitle');
        expect(jobTitleField.exists()).toBeTruthy();
        expect(jobTitleField.html()).toContain('Jobprofil');

        let primarySkillField = wrapper.find('.test_primarySkill');
        expect(primarySkillField.exists()).toBeTruthy();
        expect(primarySkillField.html()).toContain('Primärkompetenz');

        let phoneNumberField = wrapper.find('.test_phoneNumber');
        expect(phoneNumberField.exists()).toBeTruthy();
        expect(phoneNumberField.html()).toContain('Telefonnummer');

        let birthDateField = wrapper.find('.test_birthdate');
        expect(birthDateField.exists()).toBeTruthy();
        expect(birthDateField.html()).toContain('Geburtsdatum');

    })
});
