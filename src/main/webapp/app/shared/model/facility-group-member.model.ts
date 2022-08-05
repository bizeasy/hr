import { Moment } from 'moment';
import { IFacility } from 'app/shared/model/facility.model';
import { IFacilityGroup } from 'app/shared/model/facility-group.model';

export interface IFacilityGroupMember {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  sequenceNo?: number;
  facility?: IFacility;
  facilityGroup?: IFacilityGroup;
}

export class FacilityGroupMember implements IFacilityGroupMember {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public sequenceNo?: number,
    public facility?: IFacility,
    public facilityGroup?: IFacilityGroup
  ) {}
}
