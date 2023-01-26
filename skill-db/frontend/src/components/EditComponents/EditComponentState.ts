import type {MinimumDetailModelInterface} from "@/models/DetailModel";
import type {InputBlockState} from "@/components/EditComponents/inputBlockState";
import {useDetailStore} from "@/stores/DetailStore";


export class EditComponentState implements MinimumDetailModelInterface, InputBlockState {
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
                jobTitle: string, skills: string[], references: string, oldPic: string) {
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

export function emptyEditComponentState(): EditComponentState {
    return new EditComponentState
    (
        '',
        '',
        '',
        '',
        '',
        '',
        '',
        [],
        '',
        '',
    )
}

export function storeToEditComponentState(): EditComponentState {
    const detailStore = useDetailStore();
    return new EditComponentState(
        detailStore.details.getFirstName(),
        detailStore.details.getLastName(),
        detailStore.details.getDegree(),
        detailStore.details.getBirthDate(),
        detailStore.details.getPhoneNumber(),
        detailStore.details.getPrimarySkill(),
        detailStore.details.getJobTitle(),
        detailStore.details.getSkills(),
        detailStore.details.getReferences(),
        detailStore.profilePic
    );
}
