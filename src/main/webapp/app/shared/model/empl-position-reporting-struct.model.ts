import { Moment } from 'moment';

export interface IEmplPositionReportingStruct {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  comments?: string;
}

export class EmplPositionReportingStruct implements IEmplPositionReportingStruct {
  constructor(public id?: number, public fromDate?: Moment, public thruDate?: Moment, public comments?: string) {}
}
