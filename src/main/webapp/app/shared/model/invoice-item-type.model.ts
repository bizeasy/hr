export interface IInvoiceItemType {
  id?: number;
  name?: string;
  description?: string;
}

export class InvoiceItemType implements IInvoiceItemType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
