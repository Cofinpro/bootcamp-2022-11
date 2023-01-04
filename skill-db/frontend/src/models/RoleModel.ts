export class RoleModel {
    private identifier: String;
    private displayName: String;
    private description: String;

    constructor() {
        this.identifier = '';
        this.displayName = '';
        this.description = '';
    }

    getIdentifier(): String {
        return this.identifier;
    }

    setIdentifier(value: String) {
        this.identifier = value;
    }

    getDisplayName(): String {
        return this.displayName;
    }

    setDisplayName(value: String) {
        this.displayName = value;
    }

    getDescription(): String {
        return this.description;
    }

    setDescription(value: String) {
        this.description = value;
    }
}

export class ConvertToRoleModel{
    public static toRole(object: any): RoleModel {
        const roleModel = new RoleModel();
        roleModel.setIdentifier(String(object?.identifier));
        roleModel.setDisplayName(String(object?.displayName));
        roleModel.setDescription(String(object?.detailedDescription));
        return roleModel;
    }
}