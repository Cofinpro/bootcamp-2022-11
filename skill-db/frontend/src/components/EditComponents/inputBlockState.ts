
export class inputBlockState {
    firstName: string;
    lastName: string;
    jobTitle: string;
    primarySkill: string;
    birthdate: string;
    phoneNumber: string;

    constructor(firstname: string, lastname: string, jobTitle: string, primarySkill: string, birthdate: string, phoneNumber: string) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.jobTitle = jobTitle;
        this.primarySkill = primarySkill;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
    }

}
