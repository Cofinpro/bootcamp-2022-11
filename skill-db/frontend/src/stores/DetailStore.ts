import {defineStore} from "pinia";
import {ConvertToDetailModel, DetailModel} from "@/models/DetailModel";
import {useErrorStore} from "@/stores/ErrorStore";
import axiosInstance from "@/axios";

export const useDetailStore = defineStore('detailStore', {
        state: () => ({
            details: new DetailModel(),
            loading: Boolean(false),
            skills: [] as String[],
            jobs: [] as String[],
            primarys: [] as String[],
            profilePic: '',
        }),
        actions: {
            loadDemoDetails() {
                this.details = ConvertToDetailModel.toDetail({
                    id: '0',
                    age: 20,
                    firstName: "Max",
                    birthDate: "2022-12-10",
                    lastName: "Mustewrmann",
                    degree: "Master of Nothing",
                    primarySkill: "Tech",
                    jobTitle: "Consultant",
                    technologies: ["Nothing", "Less than Nothing"],
                    references: "No references,really none"
                })
            },

            async loadDetailsById(id: String) {
                this.loading = true;
                let profilePicId = null
                const errorStore = useErrorStore();
                await axiosInstance.get(`/api/v1/profiles/${id}`).then((response) => {
                    console.log(response);
                    this.details = ConvertToDetailModel.toDetail(response.data);
                    profilePicId = response.data.profilePicId
                }).catch((error) => {
                    errorStore.catchGetError(error, id);
                    console.log(error)
                });
                if (profilePicId) {
                    await axiosInstance({
                        url: `/api/v1/images/${profilePicId}`,
                        method: 'get',
                        responseType: 'blob'
                    }).then((response) => {
                        this.profilePic=URL.createObjectURL(response.data);
                    }).catch((error) => {
                        console.log(error);
                    });
                    console.log(this.profilePic);
                }
                this.loading = false;
            },

            deleteDetailsByID(id: String) {
                this.loading = true;
                const errorStore = useErrorStore();
                axiosInstance.delete(`/api/v1/profiles/${id}`).then().catch((error) => {
                    errorStore.catchDeleteError(error, id);
                });
                this.loading = false;
            },

            async createProfile(edits: DetailModel, profilePicUri: String) {
                this.loading = true;
                let image_id = null;

                console.log(edits);
                const errorStore = useErrorStore();
                if (profilePicUri) {
                    await axiosInstance.post('/api/v1/images/', {file: profilePicUri})
                        .then((response) => {
                            image_id = response.data.id;
                            console.log(image_id);
                        })
                        .catch((error) => {
                            console.log(error);
                            errorStore.catchUploadImageError(error);
                        });
                }

                await axiosInstance.post(`/api/v1/profiles/`,
                    {
                        'firstName': edits.getFirstName(),
                        'lastName': edits.getLastName(),
                        'jobTitle': edits.getJobTitle(),
                        'degree': edits.getDegree(),
                        'primaryExpertise': edits.getPrimarySkill(),
                        'referenceText': edits.getReferences(),
                        'skills': edits.getTechnologies(),
                        'phoneNumber': edits.getPhoneNumber(),
                        'email': localStorage.getItem('username'),
                        'birthDate': edits.getBirthDate(),
                        'profilePicId': image_id,
                    }).then((response) => {
                    console.log(response);
                    errorStore.toggleHasError();
                })
                    .catch((error) => {
                        console.log(error);
                        errorStore.catchPostPatchError(error);
                    });
                this.loading = false;
            },

            async updateProfile(edits: DetailModel, id: String, profilePicUri: String) {
                this.loading = true;
                console.log(edits);
                console.log(profilePicUri);
                let image_id = null;
                if (profilePicUri) {
                    await axiosInstance.post('/api/v1/images/', {file: profilePicUri})
                        .then((response) => {
                            image_id = response.data.id;
                            console.log(image_id);
                        })
                        .catch((error) => {
                            console.log(error);
                            const errorStore = useErrorStore();
                            errorStore.catchUploadImageError(error);
                        });
                }
                const errorStore = useErrorStore();
                await axiosInstance.patch(`/api/v1/profiles/${id}`,
                    {
                        'firstName': edits.getFirstName(),
                        'lastName': edits.getLastName(),
                        'jobTitle': edits.getJobTitle(),
                        'degree': edits.getDegree(),
                        'primaryExpertise': edits.getPrimarySkill(),
                        'referenceText': edits.getReferences(),
                        'skills': edits.getTechnologies(),
                        'phoneNumber': edits.getPhoneNumber(),
                        'birthDate': edits.getBirthDate(),
                        'profilePicId': image_id,
                    }).then(() => {
                    errorStore.toggleHasError();
                }).catch((error) => {
                    console.log(error);
                    errorStore.catchPostPatchError(error);
                });
                this.loading = false;
            },

            getSkills() {
                this.skills = [];
                this.loading = true;
                const errorStore = useErrorStore();
                axiosInstance.get(`/api/v1/skills`).then((response) => {
                    response.data.forEach((element: object) => {
                        this.skills.push(element.toString());
                    })
                }).catch((error) => {
                    errorStore.catchSkillsJobsPrimariesError(error, 'skills')
                    console.log(error);
                });
                this.loading = false;
            },

            getJobs() {
                this.jobs = [];
                this.loading = true;
                const errorStore = useErrorStore();
                axiosInstance.get(`/api/v1/job-titles/`).then((response) => {
                    response.data.forEach((element: String) => {
                        this.jobs.push(element)
                    })
                }).catch((error) => {
                    errorStore.catchSkillsJobsPrimariesError(error, 'Jobtitel')
                    console.log(error);
                });
                this.loading = false;
            },

            getPrimarys() {
                this.primarys = [];
                this.loading = true;
                const errorStore = useErrorStore();
                axiosInstance.get(`/api/v1/profiles/expertises`).then((response) => {
                    response.data.forEach((element: String) => {
                        this.primarys.push(element)
                    })
                }).catch((error) => {
                    errorStore.catchSkillsJobsPrimariesError(error, 'Primärkompetenz')
                    console.log(error);
                });
                this.loading = false;
            },

            setJobs(id: number, name: String) {
                this.loading = true;
                axiosInstance.post(`/api/v1/jobTitles/`,
                    {
                        'id': id,
                        'name': name,
                    }).then(() => {
                }).catch((error) => {
                    console.log(error);
                });
                this.loading = false;
            },
        },
    }
)
