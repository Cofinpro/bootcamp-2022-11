import {defineStore} from "pinia";
import {ConvertToOverviewCard, OverviewModel} from "@/models/OverviewModel";
import {ConvertToDetailModel, DetailModel} from "@/models/DetailModel";
import axios from "@/axios";

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
            axios.get(`/api/v1/profile/${id}`).then((response) =>{
                this.details = ConvertToDetailModel.toDetail(response);
            }).catch((error) => {
                console.log(error);
            });
            this.loading = false;
        }
    },
    }
    )