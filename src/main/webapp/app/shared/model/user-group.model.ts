export interface IUserGroup {
  id?: number;
  name?: string;
  description?: string;
}

export class UserGroup implements IUserGroup {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
