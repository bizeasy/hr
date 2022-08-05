export interface IProductCategoryType {
  id?: number;
  name?: string;
  description?: string;
}

export class ProductCategoryType implements IProductCategoryType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
