import { Moment } from 'moment';
import { IParty } from 'app/shared/model/party.model';
import { IPartyClassificationGroup } from 'app/shared/model/party-classification-group.model';

export interface IPartyClassification {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  party?: IParty;
  classificationGroup?: IPartyClassificationGroup;
}

export class PartyClassification implements IPartyClassification {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public party?: IParty,
    public classificationGroup?: IPartyClassificationGroup
  ) {}
}
