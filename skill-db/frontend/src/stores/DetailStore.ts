import {defineStore} from "pinia";
import {ConvertToDetailModel, DetailModel} from "@/models/DetailModel";
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
                    this.details = ConvertToDetailModel.toDetail(response.data);
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

            async createProfile(edits: DetailModel, profilePicUri: string) {
                this.loading = true;
                const errorStore = useErrorStore();
                await axiosInstance.post(`/api/v1/profiles/`,
                    {
                        'firstName': edits.getFirstName(),
                        'lastName': edits.getLastName(),
                        'jobTitle': edits.getJobTitle(),
                        'degree': edits.getDegree(),
                        'primaryExpertise': edits.getPrimarySkill(),
                        'referenceText': edits.getReferences(),
                        'skills': edits.getSkills(),
                        'phoneNumber': edits.getPhoneNumber(),
                        'email': localStorage.getItem('username'),
                        'birthDate': edits.getBirthDate(),
                        'profilePic': profilePicUri,
                    }).then((response) => {
                    errorStore.toggleHasError();
                })
                    .catch((error) => {
                        errorStore.catchPostPatchError(error);
                    });
                this.loading = false;
            },

            async updateProfile(edits: DetailModel, id: string, profilePicUri: string) {
                this.loading = true;
                const errorStore = useErrorStore();
                await axiosInstance.patch(`/api/v1/profiles/${id}`,
                    {
                        'firstName': edits.getFirstName(),
                        'lastName': edits.getLastName(),
                        'jobTitle': edits.getJobTitle(),
                        'degree': edits.getDegree(),
                        'primaryExpertise': edits.getPrimarySkill(),
                        'referenceText': edits.getReferences(),
                        'skills': edits.getSkills(),
                        'phoneNumber': edits.getPhoneNumber(),
                        'birthDate': edits.getBirthDate(),
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
