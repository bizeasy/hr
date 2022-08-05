import { IWorkEffort } from 'app/shared/model/work-effort.model';
import { IStatus } from 'app/shared/model/status.model';

export interface IWorkEffortStatus {
  id?: number;
  reason?: string;
  workEffort?: IWorkEffort;
  status?: IStatus;
}

export class WorkEffortStatus implements IWorkEffortStatus {
  constructor(public id?: number, public reason?: string, public workEffort?: IWorkEffort, public status?: IStatus) {}
}
