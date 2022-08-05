import { Moment } from 'moment';
import { IFacility } from 'app/shared/model/facility.model';
import { IContactMech } from 'app/shared/model/contact-mech.model';
import { IContactMechPurpose } from 'app/shared/model/contact-mech-purpose.model';

export interface IFacilityContactMech {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  facility?: IFacility;
  contactMech?: IContactMech;
  contactMechPurpose?: IContactMechPurpose;
}

export class FacilityContactMech implements IFacilityContactMech {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public facility?: IFacility,
    public contactMech?: IContactMech,
    public contactMechPurpose?: IContactMechPurpose
  ) {}
}
