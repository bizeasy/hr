import { Moment } from 'moment';
import { IFacility } from 'app/shared/model/facility.model';
import { IUser } from 'app/core/user/user.model';

export interface IFacilityUsageLog {
  id?: number;
  fromTime?: Moment;
  toTime?: Moment;
  remarks?: string;
  facility?: IFacility;
  cleanedBy?: IUser;
  checkedBy?: IUser;
}

export class FacilityUsageLog implements IFacilityUsageLog {
  constructor(
    public id?: number,
    public fromTime?: Moment,
    public toTime?: Moment,
    public remarks?: string,
    public facility?: IFacility,
    public cleanedBy?: IUser,
    public checkedBy?: IUser
  ) {}
}
