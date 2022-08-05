import { Moment } from 'moment';
import { IRateType } from 'app/shared/model/rate-type.model';
import { IEmplPositionType } from 'app/shared/model/empl-position-type.model';
import { IPayGrade } from 'app/shared/model/pay-grade.model';

export interface IEmplPositionTypeRate {
  id?: number;
  rateAmount?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  rateType?: IRateType;
  emplPositionType?: IEmplPositionType;
  payGrade?: IPayGrade;
}

export class EmplPositionTypeRate implements IEmplPositionTypeRate {
  constructor(
    public id?: number,
    public rateAmount?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public rateType?: IRateType,
    public emplPositionType?: IEmplPositionType,
    public payGrade?: IPayGrade
  ) {}
}
