import {defineStore} from "pinia";
import {DetailModel} from "@/models/DetailModel";
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
            async deleteDetailsByID(id: string): Promise<void> {
                this.loading = true;
                const errorStore = useErrorStore();
                await axiosInstance.delete(`/api/v1/profiles/${id}`).then(() => {
                    this.details = new DetailModel();
                }).catch((error) => {
                    errorStore.catchDeleteError(error, id);
                });
                this.loading = false;
            },

            async loadSkills(): Promise<void> {
                this.skills = [];
                this.loading = true;
                const errorStore = useErrorStore();
                await axiosInstance.get(`/api/v1/skills`).then((response) => {
                    response.data.forEach((element: object) => {
                        this.skills.push(element.toString());
                    })
                }).catch((error) => {
                    errorStore.catchSkillsJobsPrimariesError(error, 'skills');
                });
                this.loading = false;
            },
            async loadJobs(): Promise<void> {
                this.jobs = [];
                this.loading = true;
                const errorStore = useErrorStore();
                await axiosInstance.get(`/api/v1/job-titles/`).then((response) => {
                    response.data.forEach((element: string) => {
                        this.jobs.push(element)
                    })
                }).catch((error) => {
                    errorStore.catchSkillsJobsPrimariesError(error, 'Jobtitel');
                });
                this.loading = false;
            },

            async loadPrimarys(): Promise<void> {
                this.primarys = [];
                this.loading = true;
                const errorStore = useErrorStore();
                await axiosInstance.get(`/api/v1/profiles/expertises`).then((response) => {
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
