import { Moment } from 'moment';
import { IInvoice } from 'app/shared/model/invoice.model';
import { IInvoiceItem } from 'app/shared/model/invoice-item.model';
import { IOrder } from 'app/shared/model/order.model';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { IItemIssuance } from 'app/shared/model/item-issuance.model';
import { IInventoryTransfer } from 'app/shared/model/inventory-transfer.model';
import { IInventoryItemVariance } from 'app/shared/model/inventory-item-variance.model';
import { IInventoryItem } from 'app/shared/model/inventory-item.model';

export interface IInventoryItemDelegate {
  id?: number;
  sequenceNo?: number;
  effectiveDate?: Moment;
  quantityOnHandDiff?: number;
  availableToPromiseDiff?: number;
  accountingQuantityDiff?: number;
  unitCost?: number;
  remarks?: string;
  invoice?: IInvoice;
  invoiceItem?: IInvoiceItem;
  order?: IOrder;
  orderItem?: IOrderItem;
  itemIssuance?: IItemIssuance;
  inventoryTransfer?: IInventoryTransfer;
  inventoryItemVariance?: IInventoryItemVariance;
  inventoryItem?: IInventoryItem;
}

export class InventoryItemDelegate implements IInventoryItemDelegate {
  constructor(
    public id?: number,
    public sequenceNo?: number,
    public effectiveDate?: Moment,
    public quantityOnHandDiff?: number,
    public availableToPromiseDiff?: number,
    public accountingQuantityDiff?: number,
    public unitCost?: number,
    public remarks?: string,
    public invoice?: IInvoice,
    public invoiceItem?: IInvoiceItem,
    public order?: IOrder,
    public orderItem?: IOrderItem,
    public itemIssuance?: IItemIssuance,
    public inventoryTransfer?: IInventoryTransfer,
    public inventoryItemVariance?: IInventoryItemVariance,
    public inventoryItem?: IInventoryItem
  ) {}
}
