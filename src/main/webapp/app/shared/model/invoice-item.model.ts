import { IInvoice } from 'app/shared/model/invoice.model';
import { IInvoiceItemType } from 'app/shared/model/invoice-item-type.model';
import { IInventoryItem } from 'app/shared/model/inventory-item.model';
import { IProduct } from 'app/shared/model/product.model';
import { ITaxAuthorityRateType } from 'app/shared/model/tax-authority-rate-type.model';

export interface IInvoiceItem {
  id?: number;
  sequenceNo?: number;
  quantity?: number;
  amount?: number;
  invoice?: IInvoice;
  invoiceItemType?: IInvoiceItemType;
  fromInventoryItem?: IInventoryItem;
  product?: IProduct;
  taxAuthRate?: ITaxAuthorityRateType;
}

export class InvoiceItem implements IInvoiceItem {
  constructor(
    public id?: number,
    public sequenceNo?: number,
    public quantity?: number,
    public amount?: number,
    public invoice?: IInvoice,
    public invoiceItemType?: IInvoiceItemType,
    public fromInventoryItem?: IInventoryItem,
    public product?: IProduct,
    public taxAuthRate?: ITaxAuthorityRateType
  ) {}
}
