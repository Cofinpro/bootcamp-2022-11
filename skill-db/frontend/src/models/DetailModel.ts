export class DetailModel {
    private id: string;
    private firstName: string;
    private lastName: string;
    private age: number;
    private birthdate: string;
    private degree: string;
    private jobTitle: string;
    private primarySkill: string;
    private skills: string[];
    private references: string;
    private phoneNumber: string;
    private email: string;
    private ownerId: string;

    constructor() {
        this.id = '';
        this.firstName = '';
        this.lastName = '';
        this.age = 0;
        this.birthdate = '01.01.1970';
        this.jobTitle = '';
        this.degree = '';
        this.primarySkill = '';
        this.skills = [];
        this.references = '';
        this.phoneNumber = '';
        this.email = '';
        this.ownerId = '';
    }

    public getDegree(): string {
        return this.degree;
    }

    public setDegree(value: string) {
        this.degree = value;
    }

    public getId(): string {
        return this.id;
    }

    public setId(value: string) {
        this.id = value;
    }

    public getFirstName(): string {
        return this.firstName;
    }

    public setFirstName(value: string) {
        this.firstName = value;
    }

    public getLastName(): string {
        return this.lastName;
    }

    public setLastName(value: string) {
        this.lastName = value;
    }

    public getAge(): number {
        return this.age;
    }

    public setAge(value: number) {
        this.age = value;
    }

    public setBirthDate(value: string) {
        this.birthdate = value;
    }

    public getBirthDate(): string {
        return this.birthdate;
    }

    public getJobTitle(): string {
        return this.jobTitle;
    }

    public setJobTitle(value: string) {
        this.jobTitle = value;
    }

    public getPrimarySkill(): string {
        return this.primarySkill;
    }

    public setPrimarySkill(value: string) {
        this.primarySkill = value;
    }

    public getSkills(): string[] {
        return this.skills;
    }

    public setSkills(value: string[]) {
        this.skills = value;
    }

    public getReferences(): string {
        return this.references;
    }

    public setReferences(value: string) {
        this.references = value;
    }

    public getPhoneNumber(): string {
        return this.phoneNumber;
    }

    public setPhoneNumber(value: string) {
        this.phoneNumber = value;
    }

    public getEmail(): string {
        return this.email;
    }

    public setEmail(value: string) {
        this.email = value;
    }

    getOwnerId(): string {
        return this.ownerId;
    }

    setOwnerId(value: string) {
        this.ownerId = value;
    }
}

export class ConvertResponseToDetailModel {
    public static toDetail(object: any): DetailModel {
        const detailModel = new DetailModel();
        detailModel.setId(object?.id.toString());
        detailModel.setFirstName(object?.firstName.toString());
        detailModel.setLastName(object?.lastName.toString());
        detailModel.setBirthDate(this.convertDateFormatFromISO(object?.birthDate.toString()));
        detailModel.setDegree(object?.degree.toString());
        detailModel.setJobTitle(object?.jobTitle.toString());
        detailModel.setPrimarySkill(object?.primaryExpertise.toString());
        detailModel.setSkills(object?.skills.sort((a: string, b: string) => {
            if (a.toLowerCase() < b.toLowerCase()) { return -1 }
            if (a.toLowerCase() > b.toLowerCase()) { return 1 }
            return 0
        }));
        detailModel.setReferences(object?.referenceText.toString());
        detailModel.setPhoneNumber(object?.phoneNumber.toString());
        detailModel.setEmail(object?.email.toString());
        detailModel.setAge(object?.age);
        detailModel.setOwnerId(object?.ownerId.toString());
        return detailModel;
    }

    private static convertDateFormatFromISO(date: string): string {
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
    skills: string[],
    references: string,
    phoneNumber: string,
    email: string
}

export class ConvertToDetailModelForOutput {
    public static toDetail(object: DetailObject): DetailModel {
        const detailModel = new DetailModel();
        detailModel.setFirstName(object?.firstName.toString());
        detailModel.setLastName(object?.lastName.toString());
        detailModel.setBirthDate(this.convertDateToISO(object?.birthdate.toString()));
        detailModel.setDegree(object?.degree.toString());
        detailModel.setJobTitle(object?.jobTitle.toString());
        detailModel.setPrimarySkill(object?.primarySkill.toString());
        detailModel.setSkills(object?.skills);
        detailModel.setReferences(object?.references.toString());
        detailModel.setPhoneNumber(object?.phoneNumber);
        return detailModel;
    }

    private static convertDateToISO(date: string): string {
        return `${date.split(".")[2]}-${date.split(".")[1]}-${date.split(".")[0]}`;
    }
}
