export interface IRateType {
  id?: number;
  name?: string;
  description?: string;
}

export class RateType implements IRateType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
