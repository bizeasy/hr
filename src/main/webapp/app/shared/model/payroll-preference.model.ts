import { Moment } from 'moment';
import { IParty } from 'app/shared/model/party.model';
import { IDeductionType } from 'app/shared/model/deduction-type.model';
import { IPaymentMethodType } from 'app/shared/model/payment-method-type.model';
import { IPeriodType } from 'app/shared/model/period-type.model';

export interface IPayrollPreference {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  sequenceNo?: number;
  percentage?: number;
  flatAmount?: number;
  accountNumber?: string;
  bankName?: string;
  ifscCode?: string;
  branch?: string;
  employee?: IParty;
  deductionType?: IDeductionType;
  paymentMethodType?: IPaymentMethodType;
  periodType?: IPeriodType;
}

export class PayrollPreference implements IPayrollPreference {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public sequenceNo?: number,
    public percentage?: number,
    public flatAmount?: number,
    public accountNumber?: string,
    public bankName?: string,
    public ifscCode?: string,
    public branch?: string,
    public employee?: IParty,
    public deductionType?: IDeductionType,
    public paymentMethodType?: IPaymentMethodType,
    public periodType?: IPeriodType
  ) {}
}
