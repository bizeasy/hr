export interface IPaymentGatewayConfig {
  id?: number;
  name?: string;
  authUrl?: string;
  releaseUrl?: string;
  refundUrl?: string;
}

export class PaymentGatewayConfig implements IPaymentGatewayConfig {
  constructor(public id?: number, public name?: string, public authUrl?: string, public releaseUrl?: string, public refundUrl?: string) {}
}
