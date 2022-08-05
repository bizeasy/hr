export interface IDeductionType {
  id?: number;
  name?: string;
  description?: string;
}

export class DeductionType implements IDeductionType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
