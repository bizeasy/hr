import { Moment } from 'moment';
import { IProductStore } from 'app/shared/model/product-store.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IProductStoreProduct {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  sequenceNo?: number;
  productStore?: IProductStore;
  product?: IProduct;
}

export class ProductStoreProduct implements IProductStoreProduct {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public sequenceNo?: number,
    public productStore?: IProductStore,
    public product?: IProduct
  ) {}
}
