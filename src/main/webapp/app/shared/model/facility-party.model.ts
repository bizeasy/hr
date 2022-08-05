import { Moment } from 'moment';
import { IFacility } from 'app/shared/model/facility.model';
import { IParty } from 'app/shared/model/party.model';
import { IRoleType } from 'app/shared/model/role-type.model';

export interface IFacilityParty {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  facility?: IFacility;
  party?: IParty;
  roleType?: IRoleType;
}

export class FacilityParty implements IFacilityParty {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public facility?: IFacility,
    public party?: IParty,
    public roleType?: IRoleType
  ) {}
}
