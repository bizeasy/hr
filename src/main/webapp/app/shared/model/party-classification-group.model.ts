import { IPartyClassificationType } from 'app/shared/model/party-classification-type.model';

export interface IPartyClassificationGroup {
  id?: number;
  name?: string;
  description?: string;
  classificationType?: IPartyClassificationType;
}

export class PartyClassificationGroup implements IPartyClassificationGroup {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public classificationType?: IPartyClassificationType
  ) {}
}
