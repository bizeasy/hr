import { IUom } from 'app/shared/model/uom.model';

export interface IPeriodType {
  id?: number;
  name?: string;
  description?: string;
  periodLength?: number;
  uom?: IUom;
}

export class PeriodType implements IPeriodType {
  constructor(public id?: number, public name?: string, public description?: string, public periodLength?: number, public uom?: IUom) {}
}
