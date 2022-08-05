import { Moment } from 'moment';
import { IEquipment } from 'app/shared/model/equipment.model';
import { IUser } from 'app/core/user/user.model';

export interface IEquipmentUsageLog {
  id?: number;
  fromTime?: Moment;
  toTime?: Moment;
  remarks?: string;
  equipment?: IEquipment;
  cleanedBy?: IUser;
  checkedBy?: IUser;
}

export class EquipmentUsageLog implements IEquipmentUsageLog {
  constructor(
    public id?: number,
    public fromTime?: Moment,
    public toTime?: Moment,
    public remarks?: string,
    public equipment?: IEquipment,
    public cleanedBy?: IUser,
    public checkedBy?: IUser
  ) {}
}
