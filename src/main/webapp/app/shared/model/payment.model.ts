import { Moment } from 'moment';
import { IPaymentType } from 'app/shared/model/payment-type.model';
import { IPaymentMethodType } from 'app/shared/model/payment-method-type.model';
import { IStatus } from 'app/shared/model/status.model';
import { IPaymentMethod } from 'app/shared/model/payment-method.model';
import { IPaymentGatewayResponse } from 'app/shared/model/payment-gateway-response.model';
import { IParty } from 'app/shared/model/party.model';
import { IRoleType } from 'app/shared/model/role-type.model';
import { IUom } from 'app/shared/model/uom.model';

export interface IPayment {
  id?: number;
  effectiveDate?: Moment;
  paymentDate?: Moment;
  paymentRefNumber?: string;
  amount?: number;
  paymentStatus?: string;
  mihpayId?: string;
  email?: string;
  phone?: string;
  productInfo?: string;
  txnId?: string;
  actualAmount?: number;
  paymentType?: IPaymentType;
  paymentMethodType?: IPaymentMethodType;
  status?: IStatus;
  paymentMethod?: IPaymentMethod;
  paymentGatewayResponse?: IPaymentGatewayResponse;
  partyIdFrom?: IParty;
  partyIdTo?: IParty;
  roleType?: IRoleType;
  currencyUom?: IUom;
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public effectiveDate?: Moment,
    public paymentDate?: Moment,
    public paymentRefNumber?: string,
    public amount?: number,
    public paymentStatus?: string,
    public mihpayId?: string,
    public email?: string,
    public phone?: string,
    public productInfo?: string,
    public txnId?: string,
    public actualAmount?: number,
    public paymentType?: IPaymentType,
    public paymentMethodType?: IPaymentMethodType,
    public status?: IStatus,
    public paymentMethod?: IPaymentMethod,
    public paymentGatewayResponse?: IPaymentGatewayResponse,
    public partyIdFrom?: IParty,
    public partyIdTo?: IParty,
    public roleType?: IRoleType,
    public currencyUom?: IUom
  ) {}
}
