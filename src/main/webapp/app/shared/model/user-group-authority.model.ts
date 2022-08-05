import { IUserGroup } from 'app/shared/model/user-group.model';

export interface IUserGroupAuthority {
  id?: number;
  authority?: string;
  userGroup?: IUserGroup;
}

export class UserGroupAuthority implements IUserGroupAuthority {
  constructor(public id?: number, public authority?: string, public userGroup?: IUserGroup) {}
}
