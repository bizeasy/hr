import { ICustomTimePeriod } from 'app/shared/model/custom-time-period.model';

export interface ITimeSheet {
  id?: number;
  overTimeDays?: number;
  leaves?: number;
  unappliedLeaves?: number;
  halfDays?: number;
  totalWorkingHours?: number;
  timePeriod?: ICustomTimePeriod;
}

export class TimeSheet implements ITimeSheet {
  constructor(
    public id?: number,
    public overTimeDays?: number,
    public leaves?: number,
    public unappliedLeaves?: number,
    public halfDays?: number,
    public totalWorkingHours?: number,
    public timePeriod?: ICustomTimePeriod
  ) {}
}
