export interface IUomType {
  id?: number;
  name?: string;
  description?: string;
}

export class UomType implements IUomType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
