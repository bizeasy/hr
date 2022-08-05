export interface IWorkEffortType {
  id?: number;
  name?: string;
  description?: string;
}

export class WorkEffortType implements IWorkEffortType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
