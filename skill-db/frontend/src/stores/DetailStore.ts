import {defineStore} from "pinia";
import {ConvertToDetailModel, DetailModel} from "@/models/DetailModel";
import axios from "@/axios";
import {AxiosError} from "axios";

export const useDetailStore = defineStore('detailStore', {
        state: () => ({
            details: new DetailModel(),
            loading: Boolean(false),
            hasError: Boolean(false),
            errorText: new String,
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
                    this.hasError = false;
                }).catch((error) => {
                    this.hasError = true;
                    if (error.status === 401) {
                        this.errorText = 'Unauthorized'
                    } else if (error.status === 500) {
                        this.errorText = 'Unknown error occured. Please contact your administrator!'
                    }
                    console.log(this.errorText);
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

            createProfile(edits: DetailModel) {
                this.loading = true;
                console.log(edits)
                axios.post(`/api/v1/profiles/`,
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
                    this.hasError = false;
                })
                    .catch((error) => {
                        console.log(error);
                        this.hasError = true;
                        this.errorText = this.catchError(error);
                        console.log(this.errorText);
                    });
                this.loading = false;
            },

            async updateProfile(edits: DetailModel, id: number) {
                this.loading = true;
                console.log(edits);
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
                        this.hasError = false;
                }).catch((error) => {
                    this.hasError = true;
                    this.errorText = this.catchError(error);
                });
                this.loading = false;
            },

            catchError(error: AxiosError): String {
                let errorText: String;
                if (error.response == undefined) {
                    return "Unknown error";
                } else {
                    if (error.response.status === 400) {
                        errorText = "Profile content not filled correctly! Profile not saved!"
                    } else if (error.response.status === 401) {
                        errorText = 'Unauthorized'
                    } else if (error.response.status === 500) {
                        errorText = 'Unknown error occurred. Please contact your administrator!'
                    } else {
                        errorText = 'Unknown error'
                    }
                }
                return errorText;
            },

            getSkills() {
                this.skills = [];
                this.loading = true;
                axios.get(`/api/v1/skills`).then((response) => {
                    response.data.forEach((element: object) => {
                        this.skills.push(element.toString());
                    })
                }).catch((error) => {
                    console.log(error);
                });
                this.loading = false;
            },

            getJobs() {
                this.jobs = [];
                this.loading = true;
                axios.get(`/api/v1/job-titles/`).then((response) => {
                    response.data.forEach((element: String) => {
                        this.jobs.push(element)
                    })
                }).catch((error) => {
                    console.log(error);
                });
                this.loading = false;
            },

            getPrimarys() {
                this.primarys = [];
                this.loading = true;
                axios.get(`/api/v1/profiles/expertises`).then((response) => {
                    response.data.forEach((element: String) => {
                        this.primarys.push(element)
                    })
                }).catch((error) => {
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
