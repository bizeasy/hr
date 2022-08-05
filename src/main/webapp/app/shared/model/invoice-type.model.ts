export interface IInvoiceType {
  id?: number;
  name?: string;
  description?: string;
}

export class InvoiceType implements IInvoiceType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
