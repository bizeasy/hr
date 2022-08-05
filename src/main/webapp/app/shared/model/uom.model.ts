import { IUomType } from 'app/shared/model/uom-type.model';

export interface IUom {
  id?: number;
  name?: string;
  description?: string;
  sequenceNo?: number;
  abbreviation?: string;
  uomType?: IUomType;
}

export class Uom implements IUom {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public sequenceNo?: number,
    public abbreviation?: string,
    public uomType?: IUomType
  ) {}
}
