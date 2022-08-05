export interface IWorkEffortPurpose {
  id?: number;
  name?: string;
  description?: string;
}

export class WorkEffortPurpose implements IWorkEffortPurpose {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
