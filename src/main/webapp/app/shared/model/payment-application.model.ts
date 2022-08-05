import { IPayment } from 'app/shared/model/payment.model';
import { IInvoice } from 'app/shared/model/invoice.model';
import { IInvoiceItem } from 'app/shared/model/invoice-item.model';
import { IOrder } from 'app/shared/model/order.model';
import { IOrderItem } from 'app/shared/model/order-item.model';

export interface IPaymentApplication {
  id?: number;
  amountApplied?: number;
  payment?: IPayment;
  invoice?: IInvoice;
  invoiceItem?: IInvoiceItem;
  order?: IOrder;
  orderItem?: IOrderItem;
  toPayment?: IPayment;
}

export class PaymentApplication implements IPaymentApplication {
  constructor(
    public id?: number,
    public amountApplied?: number,
    public payment?: IPayment,
    public invoice?: IInvoice,
    public invoiceItem?: IInvoiceItem,
    public order?: IOrder,
    public orderItem?: IOrderItem,
    public toPayment?: IPayment
  ) {}
}
