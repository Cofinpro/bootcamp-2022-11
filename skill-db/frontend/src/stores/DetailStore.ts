import {defineStore} from "pinia";
import {ConvertToDetailModel, DetailModel} from "@/models/DetailModel";
import axios from "@/axios";
import {useErrorStore} from "@/stores/ErrorStore";

export const useDetailStore = defineStore('detailStore', {
        state: () => ({
            details: new DetailModel(),
            loading: Boolean(false),
            skills: [] as String[],
            jobs: [] as String[],
            primarys: [] as String[],
        }),
        actions: {
            loadDemoDetails() {
                this.details = ConvertToDetailModel.toDetail({
                    id: 0,
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

            loadDetailsById(id: number) {
                this.loading = true;
                axios.get(`/api/v1/profiles/${id}`).then((response) => {
                    this.details = ConvertToDetailModel.toDetail(response.data);
                }).catch((error) => {
                    console.log(error);
                });
                this.loading = false;
            },

            deleteDetailsByID(id: number) {
                this.loading = true;
                axios.delete(`/api/v1/profiles/${id}`).then().catch((error) => {
                    console.log(error);
                });
                this.loading = false;
            },

            async createProfile(edits: DetailModel) {
                this.loading = true;
                console.log(edits);
                const errorStore = useErrorStore();
                await axios.post(`/api/v1/profiles/`,
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
                    }).then(() => {
                    errorStore.toggleHasError();
                })
                    .catch((error) => {
                        console.log(error);
                        errorStore.catchPostPatchError(error);
                    });
                this.loading = false;
            },

            async updateProfile(edits: DetailModel, id: number) {
                this.loading = true;
                console.log(edits);
                const errorStore = useErrorStore()
                await axios.patch(`/api/v1/profiles/${id}`,
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
                    }).then(() =>{
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
                axios.get(`/api/v1/skills`).then((response) => {
                    response.data.forEach((element: object) => {
                        this.skills.push(element.toString());
                    })
                }).catch((error) => {
                    errorStore.catchPostPatchError(error, 'skills')
                    console.log(error);
                });
                this.loading = false;
            },

            getJobs() {
                this.jobs = [];
                this.loading = true;
                const errorStore = useErrorStore();
                axios.get(`/api/v1/job-titles/`).then((response) => {
                    response.data.forEach((element: String) => {
                        this.jobs.push(element)
                    })
                }).catch((error) => {
                    errorStore.catchPostPatchError(error, 'Jobtitel')
                    console.log(error);
                });
                this.loading = false;
            },

            getPrimarys() {
                this.primarys = [];
                this.loading = true;
                const errorStore = useErrorStore();
                axios.get(`/api/v1/profiles/expertises`).then((response) => {
                    response.data.forEach((element: String) => {
                        this.primarys.push(element)
                    })
                }).catch((error) => {
                    errorStore.catchPostPatchError(error, 'Primärkompetenz')
                    console.log(error);
                });
                this.loading = false;
            },

            setJobs(id: number, name: String) {
                this.loading = true;
                axios.post(`/api/v1/jobTitles/`,
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
