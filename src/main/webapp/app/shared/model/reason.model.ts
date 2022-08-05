import { IReasonType } from 'app/shared/model/reason-type.model';

export interface IReason {
  id?: number;
  name?: string;
  description?: string;
  reasonType?: IReasonType;
}

export class Reason implements IReason {
  constructor(public id?: number, public name?: string, public description?: string, public reasonType?: IReasonType) {}
}
