import { Moment } from 'moment';

export interface IShift {
  id?: number;
  name?: string;
  fromTime?: Moment;
  toTime?: Moment;
  workingHrs?: number;
  monthlyPaidLeaves?: number;
}

export class Shift implements IShift {
  constructor(
    public id?: number,
    public name?: string,
    public fromTime?: Moment,
    public toTime?: Moment,
    public workingHrs?: number,
    public monthlyPaidLeaves?: number
  ) {}
}
