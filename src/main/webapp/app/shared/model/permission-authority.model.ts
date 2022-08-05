export interface IPermissionAuthority {
  id?: number;
  authority?: string;
}

export class PermissionAuthority implements IPermissionAuthority {
  constructor(public id?: number, public authority?: string) {}
}
