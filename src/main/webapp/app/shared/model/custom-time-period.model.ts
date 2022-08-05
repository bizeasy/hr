import { Moment } from 'moment';
import { IPeriodType } from 'app/shared/model/period-type.model';
import { IParty } from 'app/shared/model/party.model';

export interface ICustomTimePeriod {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  isClosed?: boolean;
  periodName?: string;
  periodNum?: number;
  periodType?: IPeriodType;
  organisationParty?: IParty;
  parent?: ICustomTimePeriod;
}

export class CustomTimePeriod implements ICustomTimePeriod {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public isClosed?: boolean,
    public periodName?: string,
    public periodNum?: number,
    public periodType?: IPeriodType,
    public organisationParty?: IParty,
    public parent?: ICustomTimePeriod
  ) {
    this.isClosed = this.isClosed || false;
  }
}
