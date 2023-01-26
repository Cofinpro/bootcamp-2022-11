import {EditComponentState} from "@/components/EditComponents/EditComponentState";

export function checkDateFormat(date: string): Boolean {
    const regex = /^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\d\d$/;
    if (!regex.test(date)) return false;

    const currentDate = new Date();
    const [day, month, year] = date.split('.');
    const inputDate = new Date(Date.UTC(Number(year), Number(month), Number(day)));

    return inputDate <= currentDate;
}

export function checkPhoneNumberFormat(number: string): Boolean {
    return /^[0-9]{11,13}$/.test(number);
}

export function checkLength(value: string): Boolean {
    return value.length > 0;
}

export function checkState(stateValue: EditComponentState): Boolean {
    return (checkDateFormat(stateValue.birthDate) &&
        checkPhoneNumberFormat(stateValue.phoneNumber) &&
        checkLength(stateValue.references) &&
        checkLength(stateValue.firstName) &&
        checkLength(stateValue.lastName) &&
        checkLength(stateValue.primarySkill) &&
        checkLength(stateValue.degree) &&
        checkLength(stateValue.jobTitle))
}
