import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';
import { IProductCategory } from 'app/shared/model/product-category.model';

export interface IProductCategoryMember {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  sequenceNo?: number;
  product?: IProduct;
  productCategory?: IProductCategory;
}

export class ProductCategoryMember implements IProductCategoryMember {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public sequenceNo?: number,
    public product?: IProduct,
    public productCategory?: IProductCategory
  ) {}
}
