export class OperationsModel{
    private target: String;
    private initiator: String;
    private param: String;

    constructor() {
        this.target = '';
        this.initiator = '';
        this.param = '';
    }


    getTarget(): String {
        return this.target;
    }

    setTarget(value: String) {
        this.target = value;
    }

    getInitiator(): String {
        return this.initiator;
    }

    setInitiator(value: String) {
        this.initiator = value;
    }

    getParam(): String {
        return this.param;
    }

    setParam(value: String) {
        this.param = value;
    }
}

export class ConvertToOperationsModel{
    public static toOperationsModel(object: any): OperationsModel {
        const operation = new OperationsModel();
        operation.setTarget(String(object?.target));
        operation.setInitiator(String(object?.initiator));
        operation.setParam(String(object?.roleId));
        return operation;
    }
}