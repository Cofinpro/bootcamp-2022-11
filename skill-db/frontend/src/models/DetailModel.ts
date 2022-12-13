export class DetailModel{
    private id: number;
    private firstName: String;
    private lastName: String;
    private age: number;
    private birthdate: String;
    private degree: String;
    private jobTitle: String;
    private primarySkill: String;
    private technologies: String[];
    private references: String;
    constructor() {
        this.id = 0;
        this.firstName = '';
        this.lastName='';
        this.age = 0;
        this.birthdate = '01.01.1970';
        this.jobTitle = '';
        this.degree = '';
        this.primarySkill = '';
        this.technologies = [];
        this.references = '';
    }
    public getDegree(): String{
        return this.degree;
    }

    public setDegree(value: String){
        this.degree = value;
    }
    public getId(): number {
        return this.id;
    }

    public setId(value: number) {
        this.id = value;
    }

    public getFirstName(): String {
        return this.firstName;
    }

    public setFirstName(value: String) {
        this.firstName = value;
    }

    public getLastName(): String {
        return this.lastName;
    }

    public setLastName(value: String) {
        this.lastName = value;
    }

    public getAge(): number {
        return this.age;
    }

    public setAge(value: number) {
        this.age = value;
    }

    public setBirthDate(value: String) {
        this.birthdate = value;
    }

    public getBirthDate(): String {
        return this.birthdate;
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

    public getReferences(): String {
        return this.references;
    }

    public setReferences(value: String) {
        this.references = value;
    }
}

export class ConvertToDetailModel{
    public static toDetail(object: any): DetailModel{
        const detailModel = new DetailModel();
        detailModel.setId(Number(object?.id));
        detailModel.setFirstName(String(object?.firstName));
        detailModel.setLastName(String(object?.lastName));
        detailModel.setBirthDate(this.convertDateFormatFromISO(object?.birthDate))
        detailModel.setDegree(String(object?.degree));
        detailModel.setJobTitle(String(object?.jobTitle));
        detailModel.setPrimarySkill(String(object?.primarySkill));
        detailModel.setTechnologies(object?.technologies);
        detailModel.setReferences(object?.references);
        return detailModel;
    }
    private static convertDateFormatFromISO(date: String): String {
        return `${date.split('-')[2]}.${date.split('-')[1]}.${date.split('-')[0]}`
    }
}
