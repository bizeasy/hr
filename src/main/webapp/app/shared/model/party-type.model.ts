export interface IPartyType {
  id?: number;
  name?: string;
  description?: string;
}

export class PartyType implements IPartyType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
