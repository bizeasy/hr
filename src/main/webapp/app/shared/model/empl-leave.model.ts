import { Moment } from 'moment';
import { IParty } from 'app/shared/model/party.model';
import { IEmplLeaveType } from 'app/shared/model/empl-leave-type.model';
import { IEmplLeaveReasonType } from 'app/shared/model/empl-leave-reason-type.model';
import { IStatus } from 'app/shared/model/status.model';

export interface IEmplLeave {
  id?: number;
  description?: string;
  fromDate?: Moment;
  thruDate?: Moment;
  employee?: IParty;
  approver?: IParty;
  leaveType?: IEmplLeaveType;
  reason?: IEmplLeaveReasonType;
  status?: IStatus;
}

export class EmplLeave implements IEmplLeave {
  constructor(
    public id?: number,
    public description?: string,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public employee?: IParty,
    public approver?: IParty,
    public leaveType?: IEmplLeaveType,
    public reason?: IEmplLeaveReasonType,
    public status?: IStatus
  ) {}
}
