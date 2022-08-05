import { Moment } from 'moment';
import { IOrder } from 'app/shared/model/order.model';
import { IOrderItemType } from 'app/shared/model/order-item-type.model';
import { IInventoryItem } from 'app/shared/model/inventory-item.model';
import { IProduct } from 'app/shared/model/product.model';
import { ISupplierProduct } from 'app/shared/model/supplier-product.model';
import { IStatus } from 'app/shared/model/status.model';
import { ITaxAuthorityRateType } from 'app/shared/model/tax-authority-rate-type.model';

export interface IOrderItem {
  id?: number;
  sequenceNo?: number;
  quantity?: number;
  cancelQuantity?: number;
  selectedAmount?: number;
  unitPrice?: number;
  unitListPrice?: number;
  cgst?: number;
  igst?: number;
  sgst?: number;
  cgstPercentage?: number;
  igstPercentage?: number;
  sgstPercentage?: number;
  totalPrice?: number;
  isModifiedPrice?: boolean;
  estimatedShipDate?: Moment;
  estimatedDeliveryDate?: Moment;
  shipBeforeDate?: Moment;
  shipAfterDate?: Moment;
  order?: IOrder;
  orderItemType?: IOrderItemType;
  fromInventoryItem?: IInventoryItem;
  product?: IProduct;
  supplierProduct?: ISupplierProduct;
  status?: IStatus;
  taxAuthRate?: ITaxAuthorityRateType;
}

export class OrderItem implements IOrderItem {
  constructor(
    public id?: number,
    public sequenceNo?: number,
    public quantity?: number,
    public cancelQuantity?: number,
    public selectedAmount?: number,
    public unitPrice?: number,
    public unitListPrice?: number,
    public cgst?: number,
    public igst?: number,
    public sgst?: number,
    public cgstPercentage?: number,
    public igstPercentage?: number,
    public sgstPercentage?: number,
    public totalPrice?: number,
    public isModifiedPrice?: boolean,
    public estimatedShipDate?: Moment,
    public estimatedDeliveryDate?: Moment,
    public shipBeforeDate?: Moment,
    public shipAfterDate?: Moment,
    public order?: IOrder,
    public orderItemType?: IOrderItemType,
    public fromInventoryItem?: IInventoryItem,
    public product?: IProduct,
    public supplierProduct?: ISupplierProduct,
    public status?: IStatus,
    public taxAuthRate?: ITaxAuthorityRateType
  ) {
    this.isModifiedPrice = this.isModifiedPrice || false;
  }
}
