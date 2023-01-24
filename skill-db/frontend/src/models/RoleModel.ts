export class RoleModel {
    private identifier: string;
    private displayName: string;
    private description: string;
    private user: string[];
    private color: string;
    private defined: boolean;

    constructor() {
        this.identifier = '';
        this.displayName = '';
        this.description = '';
        this.user = [];
        this.color = '';
        this.defined = false;
    }

    getIdentifier(): string {
        return this.identifier;
    }

    setIdentifier(value: string) {
        this.identifier = value;
    }

    getDisplayName(): string {
        return this.displayName;
    }

    setDisplayName(value: string) {
        this.displayName = value;
    }

    getDescription(): string {
        return this.description;
    }

    setDescription(value: string) {
        this.description = value;
    }

    getUser(): string[] {
        return this.user;
    }

    setUser(value: []) {
        this.user = value;
    }

    getColor(): string {
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

    isDefined(): boolean {
        return this.defined;
    }

    setDefined() {
        this.defined = (this.identifier !== 'UNDEFINED');
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
        roleModel.setDefined();
        return roleModel;
    }
}