export interface ILocationType {
  id?: number;
  name?: string;
  description?: string;
}

export class LocationType implements ILocationType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
