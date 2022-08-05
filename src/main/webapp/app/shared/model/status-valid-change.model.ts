import { IStatus } from 'app/shared/model/status.model';

export interface IStatusValidChange {
  id?: number;
  transitionName?: string;
  rules?: any;
  status?: IStatus;
  statusTo?: IStatus;
}

export class StatusValidChange implements IStatusValidChange {
  constructor(public id?: number, public transitionName?: string, public rules?: any, public status?: IStatus, public statusTo?: IStatus) {}
}
