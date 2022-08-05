export interface IReasonType {
  id?: number;
  name?: string;
  description?: string;
}

export class ReasonType implements IReasonType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
