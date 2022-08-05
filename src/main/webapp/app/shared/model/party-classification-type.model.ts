export interface IPartyClassificationType {
  id?: number;
  name?: string;
  description?: string;
}

export class PartyClassificationType implements IPartyClassificationType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
