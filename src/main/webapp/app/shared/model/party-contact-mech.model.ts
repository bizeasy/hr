import { Moment } from 'moment';
import { IParty } from 'app/shared/model/party.model';
import { IContactMech } from 'app/shared/model/contact-mech.model';
import { IContactMechPurpose } from 'app/shared/model/contact-mech-purpose.model';

export interface IPartyContactMech {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  party?: IParty;
  contactMech?: IContactMech;
  contactMechPurpose?: IContactMechPurpose;
}

export class PartyContactMech implements IPartyContactMech {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public party?: IParty,
    public contactMech?: IContactMech,
    public contactMechPurpose?: IContactMechPurpose
  ) {}
}
