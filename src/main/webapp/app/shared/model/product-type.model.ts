export interface IProductType {
  id?: number;
  name?: string;
  description?: string;
}

export class ProductType implements IProductType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
