export interface IProductPriceType {
  id?: number;
  name?: string;
  description?: string;
}

export class ProductPriceType implements IProductPriceType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
