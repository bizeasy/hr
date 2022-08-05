import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';
import { IParty } from 'app/shared/model/party.model';
import { IUom } from 'app/shared/model/uom.model';

export interface ISupplierProduct {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  minOrderQty?: number;
  lastPrice?: number;
  shippingPrice?: number;
  supplierProductId?: string;
  leadTimeDays?: number;
  cgst?: number;
  igst?: number;
  sgst?: number;
  totalPrice?: number;
  comments?: string;
  supplierProductName?: string;
  manufacturerName?: string;
  product?: IProduct;
  supplier?: IParty;
  quantityUom?: IUom;
  currencyUom?: IUom;
  manufacturer?: IParty;
}

export class SupplierProduct implements ISupplierProduct {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public minOrderQty?: number,
    public lastPrice?: number,
    public shippingPrice?: number,
    public supplierProductId?: string,
    public leadTimeDays?: number,
    public cgst?: number,
    public igst?: number,
    public sgst?: number,
    public totalPrice?: number,
    public comments?: string,
    public supplierProductName?: string,
    public manufacturerName?: string,
    public product?: IProduct,
    public supplier?: IParty,
    public quantityUom?: IUom,
    public currencyUom?: IUom,
    public manufacturer?: IParty
  ) {}
}
