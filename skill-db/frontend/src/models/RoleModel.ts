export class RoleModel {
    private identifier: String;
    private displayName: String;
    private description: String;
    private user: String[];
    private color: String;

    constructor() {
        this.identifier = '';
        this.displayName = '';
        this.description = '';
        this.user = [];
        this.color = '';
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

    getUser(): String[] {
        return this.user;
    }

    setUser(value: []) {
        this.user = value;
    }

    getColor(): String {
        return this.color;
    }

    setColor() {
        if (this.identifier === 'ADMIN') {
            this.color = '#ec7b1a';
        } else if (this.identifier === 'HR') {
            this.color = '#9bc3ee';
        } else if (this.identifier === 'USER') {
            this.color = '#3a3a3a';
        } else {
            this.color = 'black';
        }
    }
}

export class ConvertToRoleModel {
    public static toRoleModel(object: any): RoleModel {
        const roleModel = new RoleModel();
        roleModel.setIdentifier(String(object?.identifier));
        roleModel.setDisplayName(String(object?.displayName));
        roleModel.setDescription(String(object?.detailedDescription));
        //roleModel.setUser(Array(object?.users));
        roleModel.setColor();
        return roleModel;
    }
}