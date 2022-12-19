import {defineStore} from "pinia";
import {ConvertToOverviewCard, OverviewModel} from "@/models/OverviewModel";
import {ConvertToDetailModel, DetailModel} from "@/models/DetailModel";
import axios from "@/axios";
import EditComponent from "@/components/EditComponent.vue";

export const useDetailStore = defineStore('detailStore',{
    state: () => ({
        details: new DetailModel(),
        loading: Boolean(false),
    }),
    actions: {
        loadDemoDetails(){
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
        loadDetailsById(id: number){
            this.loading = true;
            axios.get(`/api/v1/profiles/${id}`).then((response) =>{
                this.details = ConvertToDetailModel.toDetail(response);
            }).catch((error) => {
                console.log(error);
            });
            this.loading = false;
        },
        deleteDetailsByID(id: number) {
            this.loading = true;
            axios.delete(`/api/v1/profiles/${id}`).then(() =>{}).catch((error) => {
                console.log(error);
            });
            this.loading = false;
        },
        createProfile(edits: DetailModel) {
            this.loading = true;
            axios.post(`/api/v1/profiles/`,
                {
                    'firstName': edits.getFirstName(),
                    'lastName': edits.getLastName(),
                    'jobTitle': edits.getJobTitle(),
                    'degree': edits.getDegree(),
                    'primaryExpertise': edits.getPrimarySkill(),
                    'referenceText': edits.getReferences(),
                    'skills': edits.getTechnologies(),
                    'phoneNumber': "12312312312",
                    'email': "test@test.com",
                    'birthDate': edits.getBirthDate()
                }).then(() =>{}).catch((error) => {
                console.log(error);
            });
            this.loading = false;
        },
        updateProfile(edits: DetailModel, id: number) {
            this.loading = true;
            axios.post(`/api/v1/profiles/${id}`,
                {
                'firstName': edits.getFirstName(),
                'lastName': edits.getLastName(),
                'jobTitle': edits.getJobTitle(),
                'degree': edits.getDegree(),
                'primaryExpertise': edits.getPrimarySkill(),
                'referenceText': edits.getReferences(),
                'skills': edits.getTechnologies(),
                'phoneNumber': "123123123123",
                'email': "test@test.com",
                'birthDate': edits.getBirthDate()
            }).then(() =>{}).catch((error) => {
                console.log(error);
            });
            this.loading = false;
        },
    },
    }
    )
