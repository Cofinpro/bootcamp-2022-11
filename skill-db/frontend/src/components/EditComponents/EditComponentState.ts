import type {MinimumDetailModelInterface} from "@/models/DetailModel";
import InputBlock from "@/components/EditComponents/inputBlock.vue";
import type {InputBlockState} from "@/components/EditComponents/inputBlockState";

export class EditComponentState implements MinimumDetailModelInterface, InputBlockState{
    firstName: string;
    lastName: string;
    degree: string;
    birthDate: string;
    phoneNumber: string;
    primarySkill: string;
    jobTitle: string;
    skills: string[];
    references: string;
    profilePicUri: string;
    oldPic: string;
    constructor(firstName: string, lastName: string, degree: string,
                birthDate: string, phoneNumber: string, primarySkill: string,
                jobTitle: string, skills: string[], references: string,  oldPic: string) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.primarySkill = primarySkill;
        this.jobTitle = jobTitle;
        this.skills = skills;
        this.references = references;
        this.oldPic = oldPic;
        this.profilePicUri = '';
    }
}
