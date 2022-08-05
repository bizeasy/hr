import { IOrderItem } from 'app/shared/model/order-item.model';
import { IInvoiceItem } from 'app/shared/model/invoice-item.model';

export interface IOrderItemBilling {
  id?: number;
  quantity?: number;
  amount?: number;
  orderItem?: IOrderItem;
  invoiceItem?: IInvoiceItem;
}

export class OrderItemBilling implements IOrderItemBilling {
  constructor(
    public id?: number,
    public quantity?: number,
    public amount?: number,
    public orderItem?: IOrderItem,
    public invoiceItem?: IInvoiceItem
  ) {}
}
