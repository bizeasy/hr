import { IShift } from 'app/shared/model/shift.model';
import { DayOfheWeek } from 'app/shared/model/enumerations/day-ofhe-week.model';

export interface IShiftHolidays {
  id?: number;
  dayOfheWeek?: DayOfheWeek;
  monthlyPaidLeaves?: number;
  yearlyPaidLeaves?: number;
  shift?: IShift;
}

export class ShiftHolidays implements IShiftHolidays {
  constructor(
    public id?: number,
    public dayOfheWeek?: DayOfheWeek,
    public monthlyPaidLeaves?: number,
    public yearlyPaidLeaves?: number,
    public shift?: IShift
  ) {}
}
