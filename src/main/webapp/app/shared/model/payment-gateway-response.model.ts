import { IPaymentMethodType } from 'app/shared/model/payment-method-type.model';

export interface IPaymentGatewayResponse {
  id?: number;
  amount?: number;
  referenceNumber?: string;
  gatewayMessage?: string;
  paymentMethodType?: IPaymentMethodType;
}

export class PaymentGatewayResponse implements IPaymentGatewayResponse {
  constructor(
    public id?: number,
    public amount?: number,
    public referenceNumber?: string,
    public gatewayMessage?: string,
    public paymentMethodType?: IPaymentMethodType
  ) {}
}
