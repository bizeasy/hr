export interface IPermission {
  id?: number;
  name?: string;
  description?: string;
}

export class Permission implements IPermission {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
