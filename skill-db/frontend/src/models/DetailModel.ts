export class DetailModel{
    private id: number;
    private name: String;
    private age: number;
    private jobTitle: String;
    private primarySkill: String;
    private technologies: String[];
    private references: String[];
    constructor() {
        this.id = 0;
        this.name = '';
        this.age = 0;
        this.jobTitle = '';
        this.primarySkill = '';
        this.technologies = [];
        this.references = [];
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

    public getAge(): number {
        return this.age;
    }

    public setAge(value: number) {
        this.age = value;
    }

    public getJobTitle(): String {
        return this.jobTitle;
    }

    public setJobTitle(value: String) {
        this.jobTitle = value;
    }

    public getPrimarySkill(): String {
        return this.primarySkill;
    }

    public setPrimarySkill(value: String) {
        this.primarySkill = value;
    }

    public getTechnologies(): String[] {
        return this.technologies;
    }

    public setTechnologies(value: String[]) {
        this.technologies = value;
    }

    public getReferences(): String[] {
        return this.references;
    }

    public setReferences(value: String[]) {
        this.references = value;
    }
}

export class ConvertToDetailModel{
    public static toDetail(object: any): DetailModel{
        const detailModel = new DetailModel();
        detailModel.setId(Number(object?.id));
        detailModel.setName(String(object?.name));
        detailModel.setJobTitle(String(object?.jobTitle));
        detailModel.setPrimarySkill(String(object?.primarySkill))
        detailModel.setTechnologies(object?.technologies);
        detailModel.setReferences(object?.references);
        return detailModel;
    }
}
