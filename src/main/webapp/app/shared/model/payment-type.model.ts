export interface IPaymentType {
  id?: number;
  name?: string;
  description?: string;
}

export class PaymentType implements IPaymentType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
