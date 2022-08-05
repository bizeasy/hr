export interface IPaymentMethodType {
  id?: number;
  name?: string;
  description?: string;
}

export class PaymentMethodType implements IPaymentMethodType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
