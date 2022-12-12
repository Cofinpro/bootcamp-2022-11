import {defineStore} from "pinia";
import {ConvertToOverviewCard, OverviewModel} from "@/models/OverviewModel";
import {ConvertToDetailModel, DetailModel} from "@/models/DetailModel";

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
                name: "Max Mustermann",
                primarySkill: "Vue",
                jobTitle: "Consultant",
                technologies: ["Nothing", "Less than Nothing"],
                references: ["No references", "really none"]
            })
        },
    },
    }
    )
