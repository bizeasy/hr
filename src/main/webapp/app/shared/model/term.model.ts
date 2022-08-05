import { ITermType } from 'app/shared/model/term-type.model';

export interface ITerm {
  id?: number;
  name?: string;
  description?: string;
  termDetail?: string;
  termValue?: number;
  termDays?: number;
  textValue?: string;
  termType?: ITermType;
}

export class Term implements ITerm {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public termDetail?: string,
    public termValue?: number,
    public termDays?: number,
    public textValue?: string,
    public termType?: ITermType
  ) {}
}
