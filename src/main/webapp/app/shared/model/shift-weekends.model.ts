import { Moment } from 'moment';
import { IShift } from 'app/shared/model/shift.model';

export interface IShiftWeekends {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  shift?: IShift;
}

export class ShiftWeekends implements IShiftWeekends {
  constructor(public id?: number, public fromDate?: Moment, public thruDate?: Moment, public shift?: IShift) {}
}
