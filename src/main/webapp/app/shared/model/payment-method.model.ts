import { Moment } from 'moment';
import { IPaymentMethodType } from 'app/shared/model/payment-method-type.model';
import { IParty } from 'app/shared/model/party.model';

export interface IPaymentMethod {
  id?: number;
  name?: string;
  description?: string;
  fromDate?: Moment;
  thruDate?: Moment;
  paymentMethodType?: IPaymentMethodType;
  party?: IParty;
}

export class PaymentMethod implements IPaymentMethod {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public paymentMethodType?: IPaymentMethodType,
    public party?: IParty
  ) {}
}
