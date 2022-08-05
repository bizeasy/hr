import { Moment } from 'moment';
import { IUserGroup } from 'app/shared/model/user-group.model';
import { IUser } from 'app/core/user/user.model';

export interface IUserGroupMember {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  userGroup?: IUserGroup;
  user?: IUser;
}

export class UserGroupMember implements IUserGroupMember {
  constructor(public id?: number, public fromDate?: Moment, public thruDate?: Moment, public userGroup?: IUserGroup, public user?: IUser) {}
}
