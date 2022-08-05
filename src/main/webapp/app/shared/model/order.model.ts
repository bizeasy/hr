import { Moment } from 'moment';
import { IOrderType } from 'app/shared/model/order-type.model';
import { ISalesChannel } from 'app/shared/model/sales-channel.model';
import { IParty } from 'app/shared/model/party.model';
import { IStatus } from 'app/shared/model/status.model';

export interface IOrder {
  id?: number;
  name?: string;
  externalId?: string;
  orderDate?: Moment;
  priority?: number;
  entryDate?: Moment;
  isRushOrder?: boolean;
  needsInventoryIssuance?: boolean;
  remainingSubTotal?: number;
  grandTotal?: number;
  hasRateContract?: boolean;
  gotQuoteFromVendors?: boolean;
  vendorReason?: string;
  expectedDeliveryDate?: Moment;
  insuranceResp?: string;
  transportResp?: string;
  unloadingResp?: string;
  orderType?: IOrderType;
  salesChannel?: ISalesChannel;
  party?: IParty;
  status?: IStatus;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public name?: string,
    public externalId?: string,
    public orderDate?: Moment,
    public priority?: number,
    public entryDate?: Moment,
    public isRushOrder?: boolean,
    public needsInventoryIssuance?: boolean,
    public remainingSubTotal?: number,
    public grandTotal?: number,
    public hasRateContract?: boolean,
    public gotQuoteFromVendors?: boolean,
    public vendorReason?: string,
    public expectedDeliveryDate?: Moment,
    public insuranceResp?: string,
    public transportResp?: string,
    public unloadingResp?: string,
    public orderType?: IOrderType,
    public salesChannel?: ISalesChannel,
    public party?: IParty,
    public status?: IStatus
  ) {
    this.isRushOrder = this.isRushOrder || false;
    this.needsInventoryIssuance = this.needsInventoryIssuance || false;
    this.hasRateContract = this.hasRateContract || false;
    this.gotQuoteFromVendors = this.gotQuoteFromVendors || false;
  }
}
