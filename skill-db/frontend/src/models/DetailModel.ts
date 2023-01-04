export class DetailModel {
    private id: String;
    private firstName: String;
    private lastName: String;
    private age: number;
    private birthdate: String;
    private degree: String;
    private jobTitle: String;
    private primarySkill: String;
    private technologies: String[];
    private references: String;
    private phoneNumber: String;
    private email: String;

    constructor() {
        this.id = '';
        this.firstName = '';
        this.lastName = '';
        this.age = 0;
        this.birthdate = '01.01.1970';
        this.jobTitle = '';
        this.degree = '';
        this.primarySkill = '';
        this.technologies = [];
        this.references = '';
        this.phoneNumber = '';
        this.email = '';
    }

    public getDegree(): String {
        return this.degree;
    }

    public setDegree(value: String) {
        this.degree = value;
    }

    public getId(): String {
        return this.id;
    }

    public setId(value: String) {
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

    public getPhoneNumber(): String {
        return this.phoneNumber;
    }

    public setPhoneNumber(value: String) {
        this.phoneNumber = value;
    }

    public getEmail(): String {
        return this.email;
    }

    public setEmail(value: String) {
        this.email = value;
    }
}

export class ConvertToDetailModel {
    public static toDetail(object: any): DetailModel {
        const detailModel = new DetailModel();
        detailModel.setId(String(object?.id));
        detailModel.setFirstName(String(object?.firstName));
        detailModel.setLastName(String(object?.lastName));
        detailModel.setBirthDate(this.convertDateFormatFromISO(String(object?.birthDate)));
        detailModel.setDegree(String(object?.degree));
        detailModel.setJobTitle(String(object?.jobTitle));
        detailModel.setPrimarySkill(String(object?.primaryExpertise));
        detailModel.setTechnologies(object?.skills);
        detailModel.setReferences(object?.referenceText);
        detailModel.setPhoneNumber(object?.phoneNumber);
        detailModel.setEmail(object?.email);
        detailModel.setAge(object?.age);
        return detailModel;
    }

    private static convertDateFormatFromISO(date: String): String {
        return `${date.split('-')[2]}.${date.split('-')[1]}.${date.split('-')[0]}`
    }
}

interface DetailObject {
    firstName: string,
    lastName: string,
    birthdate: string,
    degree: string,
    jobTitle: string,
    primarySkill: string,
    technologies: string[],
    references: string,
    phoneNumber: string,
    email: string
}

export class ConvertToDetailModelForOutput {
    public static toDetail(object: DetailObject): DetailModel {
        const detailModel = new DetailModel();
        detailModel.setFirstName(String(object?.firstName));
        detailModel.setLastName(String(object?.lastName));
        detailModel.setBirthDate(this.convertDateToISO(object?.birthdate));
        detailModel.setDegree(String(object?.degree));
        detailModel.setJobTitle(String(object?.jobTitle));
        detailModel.setPrimarySkill(String(object?.primarySkill));
        detailModel.setTechnologies(object?.technologies);
        detailModel.setReferences(object?.references);
        detailModel.setPhoneNumber(String(object?.phoneNumber));
        detailModel.setEmail(String(object?.email));
        return detailModel;
    }

    private static convertDateToISO(date: String): String {
        return `${date.split(".")[2]}-${date.split(".")[1]}-${date.split(".")[0]}`;
    }
}
