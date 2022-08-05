import { Moment } from 'moment';
import { IWorkEffortAssocType } from 'app/shared/model/work-effort-assoc-type.model';
import { IWorkEffort } from 'app/shared/model/work-effort.model';

export interface IWorkEffortAssoc {
  id?: number;
  sequenceNo?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  type?: IWorkEffortAssocType;
  weIdFrom?: IWorkEffort;
  weIdTo?: IWorkEffort;
}

export class WorkEffortAssoc implements IWorkEffortAssoc {
  constructor(
    public id?: number,
    public sequenceNo?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public type?: IWorkEffortAssocType,
    public weIdFrom?: IWorkEffort,
    public weIdTo?: IWorkEffort
  ) {}
}
