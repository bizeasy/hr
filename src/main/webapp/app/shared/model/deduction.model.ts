import { IDeductionType } from 'app/shared/model/deduction-type.model';

export interface IDeduction {
  id?: number;
  amount?: number;
  type?: IDeductionType;
}

export class Deduction implements IDeduction {
  constructor(public id?: number, public amount?: number, public type?: IDeductionType) {}
}
