export interface IWorkEffortAssocType {
  id?: number;
  name?: string;
  description?: string;
}

export class WorkEffortAssocType implements IWorkEffortAssocType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
