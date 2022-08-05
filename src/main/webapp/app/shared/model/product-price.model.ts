import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';
import { IProductPriceType } from 'app/shared/model/product-price-type.model';
import { IProductPricePurpose } from 'app/shared/model/product-price-purpose.model';
import { IUom } from 'app/shared/model/uom.model';

export interface IProductPrice {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  price?: number;
  cgst?: number;
  igst?: number;
  sgst?: number;
  totalPrice?: number;
  product?: IProduct;
  productPriceType?: IProductPriceType;
  productPricePurpose?: IProductPricePurpose;
  currencyUom?: IUom;
}

export class ProductPrice implements IProductPrice {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public price?: number,
    public cgst?: number,
    public igst?: number,
    public sgst?: number,
    public totalPrice?: number,
    public product?: IProduct,
    public productPriceType?: IProductPriceType,
    public productPricePurpose?: IProductPricePurpose,
    public currencyUom?: IUom
  ) {}
}
