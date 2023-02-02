import type {MinimumDetailModelInterface} from "@/models/DetailModel";
import {useDetailStore} from "@/stores/DetailStore";
import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";
import router from "@/router";
import {useUserStore} from "@/stores/UserStore";

export async function createProfile(profile: MinimumDetailModelInterface, profilePicUri: string): Promise<void>{
    const detailStore = useDetailStore();
    detailStore.loading = true;
    const errorStore = useErrorStore();
    await axiosInstance.post(`/api/v1/profiles/`,
        {
            'firstName': profile.firstName,
            'lastName': profile.lastName,
            'jobTitle': profile.jobTitle,
            'degree': profile.degree,
            'primaryExpertise': profile.primarySkill,
            'referenceText': profile.references,
            'skills': profile.skills,
            'phoneNumber': profile.phoneNumber,
            'email': localStorage.getItem('username'),
            'birthDate': convertDateToISO(profile.birthDate),
            'profilePic': profilePicUri,
        }).then(() => {
        errorStore.toggleHasError();
    })
        .catch((error) => {
            errorStore.catchAllAxiosErrors(error, 'PostPatch',  '');
        });
    detailStore.loading = false;
    const userStore = useUserStore()
    userStore.hasProfile = true;
    await router.push('/');
}

export async function updateProfile(edits: MinimumDetailModelInterface, profilePicUri: string, picToDelete: boolean) {
    const detailStore = useDetailStore();
    const errorStore = useErrorStore();
    if (picToDelete) {
        await deleteProfilePicture();
    }
    detailStore.loading = true
    await axiosInstance.patch(`/api/v1/profiles/${detailStore.details.getId()}`,
        {
            'firstName': edits.firstName,
            'lastName': edits.lastName,
            'jobTitle': edits.jobTitle,
            'degree': edits.degree,
            'primaryExpertise': edits.primarySkill,
            'referenceText': edits.references,
            'skills': edits.skills,
            'phoneNumber': edits.phoneNumber,
            'birthDate': convertDateToISO(edits.birthDate),
            'profilePic': profilePicUri,
        }).then(() => {
        errorStore.toggleHasError();
    }).catch((error) => {
        console.log(error);
        errorStore.catchAllAxiosErrors(error, 'PostPatch', '');
    });
    detailStore.loading = false;
    await router.push(`/detail/${detailStore.details.getId()}`)
}

async function deleteProfilePicture() {
    const detailStore = useDetailStore();
    const id = detailStore.details.getId();
    const errorStore = useErrorStore();
    await axiosInstance.delete(`/api/v1/images/${detailStore.details.getId()}`)
        .then((response) => {
            console.log(response);
        })
        .catch((error) => {
            console.log(error)
            errorStore.catchAllAxiosErrors(error, 'Delete', id);
        })
}

export async function loadAvailableSkills(): Promise<string[]> {
    let availableSkills: string[] = [];
    const errorStore = useErrorStore();
    await axiosInstance.get(`/api/v1/skills`).then((response) => {
        response.data.forEach((element: object) => {
            availableSkills.push(element.toString());
        })
    }).catch((error) => {
        errorStore.catchSkillsJobsPrimariesError(error, 'skills');
    });
    return availableSkills;
}

export async function loadJobs(): Promise<string[]> {
    let jobs: string[] = [];
    const errorStore = useErrorStore();
    await axiosInstance.get(`/api/v1/job-titles/`).then((response) => {
        response.data.forEach((element: string) => {
            jobs.push(element)
        })
    }).catch((error) => {
        errorStore.catchSkillsJobsPrimariesError(error, 'Jobtitel');
    });
    return jobs;
}

export async function loadPrimarys(): Promise<string[]> {
    let primarys: string[] = [];
    const errorStore = useErrorStore();
    await axiosInstance.get(`/api/v1/profiles/expertises`).then((response) => {
        response.data.forEach((element: string) => {
            primarys.push(element)
        })
    }).catch((error) => {
        errorStore.catchSkillsJobsPrimariesError(error, 'Primärkompetenz');
    });
    return primarys;
}

function convertDateToISO(date: string): string {
    return `${date.split(".")[2]}-${date.split(".")[1]}-${date.split(".")[0]}`;
}

