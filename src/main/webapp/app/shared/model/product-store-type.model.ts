export interface IProductStoreType {
  id?: number;
  name?: string;
  description?: string;
}

export class ProductStoreType implements IProductStoreType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
