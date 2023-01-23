import {defineStore} from "pinia";
import {ConvertResponseToDetailModel, ConvertToDetailModelForOutput, DetailModel,type MinimumDetailModelInterface} from "@/models/DetailModel";
import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";

export const useDetailStore = defineStore('detailStore', {
        state: () => ({
            details: new DetailModel(),
            loading: Boolean(false),
            skills: [] as string[],
            jobs: [] as string[],
            primarys: [] as string[],
            profilePic: '',
        }),
        actions: {
            async loadDetailsById(id: string) {
                this.loading = true;
                let profilePicId = null
                const errorStore = useErrorStore();
                await axiosInstance.get(`/api/v1/profiles/${id}`).then((response) => {
                    console.log(response);
                    this.details = ConvertResponseToDetailModel.toDetail(response.data);
                    profilePicId = response.data.profilePicId
                }).catch((error) => {
                    errorStore.catchGetError(error, id);
                });
                await axiosInstance({
                    url: `/api/v1/images/${profilePicId}`,
                    method: 'get',
                    responseType: 'blob'
                }).then((response) => {
                    if (response.data.size === 0) {
                        this.profilePic = '';
                    } else {
                        this.profilePic = URL.createObjectURL(response.data);
                    }
                }).catch((error) => {
                    console.log(error);
                    errorStore.catchDownloadImageError(error);
                })
                this.loading = false;
            },

            deleteDetailsByID(id: string) {
                this.loading = true;
                const errorStore = useErrorStore();
                axiosInstance.delete(`/api/v1/profiles/${id}`).then().catch((error) => {
                    errorStore.catchDeleteError(error, id);
                });
                this.loading = false;
            },

            async createProfile(edits: MinimumDetailModelInterface, profilePicUri: string) {
                this.loading = true
                const errorStore = useErrorStore();
                edits.birthDate = ConvertToDetailModelForOutput.convertDateToISO(edits.birthDate)
                await axiosInstance.post(`/api/v1/profiles/`,
                    {
                        'firstName': edits.firstName,
                        'lastName': edits.lastName,
                        'jobTitle': edits.jobTitle,
                        'degree': edits.degree,
                        'primaryExpertise': edits.primarySkill,
                        'referenceText': edits.references,
                        'skills': edits.skills,
                        'phoneNumber': edits.phoneNumber,
                        'email': localStorage.getItem('username'),
                        'birthDate': edits.birthDate,
                        'profilePic': profilePicUri,
                    }).then((response) => {
                    errorStore.toggleHasError();
                })
                    .catch((error) => {
                        errorStore.catchPostPatchError(error);
                    });
                this.loading = false;
            },

            async updateProfile(edits: MinimumDetailModelInterface, id: string, profilePicUri: string) {
                this.loading = true;
                const errorStore = useErrorStore();
                edits.birthDate = ConvertToDetailModelForOutput.convertDateToISO(edits.birthDate)
                await axiosInstance.patch(`/api/v1/profiles/${id}`,
                    {
                        'firstName': edits.firstName,
                        'lastName': edits.lastName,
                        'jobTitle': edits.jobTitle,
                        'degree': edits.degree,
                        'primaryExpertise': edits.primarySkill,
                        'referenceText': edits.references,
                        'skills': edits.skills,
                        'phoneNumber': edits.phoneNumber,
                        'birthDate': edits.birthDate,
                        'profilePic': profilePicUri,
                    }).then(() => {
                    errorStore.toggleHasError();
                }).catch((error) => {
                    console.log(error);
                    errorStore.catchPostPatchError(error);
                });
                this.loading = false;
            },

            async deleteProfilePictureByProfileId(id: string) {
                this.loading = true;
                const errorStore = useErrorStore();
                await axiosInstance.delete(`/api/v1/images/${id}`)
                    .then((response) => {
                        console.log(response);
                    })
                    .catch((error) => {
                        console.log(error)
                        errorStore.catchDeleteError(error, id);
                    })
                this.loading = false;
            },

            loadSkills() {
                this.skills = [];
                this.loading = true;
                const errorStore = useErrorStore();
                axiosInstance.get(`/api/v1/skills`).then((response) => {
                    response.data.forEach((element: object) => {
                        this.skills.push(element.toString());
                    })
                }).catch((error) => {
                    errorStore.catchSkillsJobsPrimariesError(error, 'skills');
                });
                this.loading = false;
            },
            loadJobs() {
                this.jobs = [];
                this.loading = true;
                const errorStore = useErrorStore();
                axiosInstance.get(`/api/v1/job-titles/`).then((response) => {
                    response.data.forEach((element: string) => {
                        this.jobs.push(element)
                    })
                }).catch((error) => {
                    errorStore.catchSkillsJobsPrimariesError(error, 'Jobtitel');
                });
                this.loading = false;
            },

            loadPrimarys() {
                this.primarys = [];
                this.loading = true;
                const errorStore = useErrorStore();
                axiosInstance.get(`/api/v1/profiles/expertises`).then((response) => {
                    response.data.forEach((element: string) => {
                        this.primarys.push(element)
                    })
                }).catch((error) => {
                    errorStore.catchSkillsJobsPrimariesError(error, 'Primärkompetenz');
                });
                this.loading = false;
            },
        },
    }
)
