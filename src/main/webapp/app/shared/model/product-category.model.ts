import { IProductCategoryType } from 'app/shared/model/product-category-type.model';

export interface IProductCategory {
  id?: number;
  name?: string;
  description?: string;
  longDescription?: string;
  attribute?: string;
  sequenceNo?: number;
  imageUrl?: string;
  altImageUrl?: string;
  info?: any;
  productCategoryType?: IProductCategoryType;
  parent?: IProductCategory;
}

export class ProductCategory implements IProductCategory {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public longDescription?: string,
    public attribute?: string,
    public sequenceNo?: number,
    public imageUrl?: string,
    public altImageUrl?: string,
    public info?: any,
    public productCategoryType?: IProductCategoryType,
    public parent?: IProductCategory
  ) {}
}
