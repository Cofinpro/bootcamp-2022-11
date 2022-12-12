import OverviewCard from "@/components/OverviewCard.vue";

export class OverviewModel{
    private id: number;
    private name: String;
    private jobTitle: String;
    private primarySkill: String;

    constructor() {
        this.id = 0;
        this.name = '';
        this.jobTitle = '';
        this.primarySkill = '';
    }

    public getId(): number {
        return this.id;
    }

    public setId(value: number) {
        this.id = value;
    }

    public getName(): String {
        return this.name;
    }

    public setName(value: String) {
        this.name = value;
    }

    public setJobTitle(value: String) {
        this.jobTitle = value;
    }

    public setPrimarySkill(value: String) {
        this.primarySkill = value;
    }

    public getJobTitle(): String {
        return this.jobTitle;
    }

    public getPrimarySkill(): String {
        return this.primarySkill;
    }
}

export class ConvertToOverviewCard{
    public static toOverviewCard(object: any): OverviewModel {
        const overviewCard = new OverviewModel();
        overviewCard.setId(object?.id);
        overviewCard.setName(object?.name);
        overviewCard.setJobTitle(object?.jobTitle);
        overviewCard.setPrimarySkill(object?.primarySkill);
        return overviewCard;
    }
}