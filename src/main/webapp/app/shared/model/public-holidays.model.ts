import { Moment } from 'moment';
import { IHolidayType } from 'app/shared/model/holiday-type.model';

export interface IPublicHolidays {
  id?: number;
  name?: string;
  fromDate?: Moment;
  thruDate?: Moment;
  noOfHolidays?: number;
  type?: IHolidayType;
}

export class PublicHolidays implements IPublicHolidays {
  constructor(
    public id?: number,
    public name?: string,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public noOfHolidays?: number,
    public type?: IHolidayType
  ) {}
}
