import {ConvertToRoleModel, RoleModel} from "@/models/RoleModel";

export class UserModel {
    private email: String;
    private role: RoleModel;
    private locked: boolean;
    private emailConfirmed: boolean;

    constructor() {
        this.email = '';
        this.role = new RoleModel();
        this.locked = false;
        this.emailConfirmed = false;
    }

    getEmail(): String {
        return this.email;
    }

    setEmail(value: String) {
        this.email = value;
    }

    getRole(): RoleModel {
        return this.role;
    }

    setRole(value: RoleModel) {
        this.role = value;
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
        userModel.setRole(ConvertToRoleModel.toRole(object?.role));
        userModel.setLocked(Boolean(object?.locked));
        userModel.setEmailConfirmed(Boolean(object?.emailConfirmed));
        return userModel;
    }
}
