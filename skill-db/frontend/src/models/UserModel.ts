export class UserModel {
    private email: String;
    private role: String;
    private rights: String[];
    private locked: boolean;
    private emailConfirmed: boolean;

    constructor() {
        this.email = '';
        this.role = '';
        this.rights=[];
        this.locked = false;
        this.emailConfirmed = false;
    }

    getEmail(): String {
        return this.email;
    }

    setEmail(value: String) {
        this.email = value;
    }

    getRole(): String {
        return this.role;
    }

    setRole(value: String) {
        this.role = value;
    }

    getRights(): String[] {
        return this.rights;
    }

    setRights(value: String[]) {
        this.rights = value;
    }
    getLocked(): boolean {
        return this.locked;
    }

    setLocked(value: boolean) {
        this.locked = value;
    }

    getEmailConfirmed(): boolean {
        return this.emailConfirmed;
    }

    setEmailConfirmed(value: boolean) {
        this.emailConfirmed = value;
    }
}

export class ConvertToUserModel{
    public static toUserModel(object: any): UserModel {
        const userModel = new UserModel();
        userModel.setEmail(String(object?.email));
        userModel.setRole(String(object?.role));
        userModel.setRights(object?.userRights);
        userModel.setLocked(Boolean(object?.locked));
        userModel.setEmailConfirmed(Boolean(object?.emailConfirmed));
        return userModel;
    }
}
