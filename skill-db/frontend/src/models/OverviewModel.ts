export class OverviewModel{
    private id: String;
    private name: String;
    private jobTitle: String;
    private primarySkill: String;

    constructor() {
        this.id = '';
        this.name = '';
        this.jobTitle = '';
        this.primarySkill = '';
    }

    public getId(): String {
        return this.id;
    }

    public setId(value: String) {
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
        overviewCard.setId(String(object?.outerId));
        overviewCard.setName(String(object?.name));
        overviewCard.setJobTitle(String(object?.jobTitle));
        overviewCard.setPrimarySkill(String(object?.primaryExpertise));
        return overviewCard;
    }
}