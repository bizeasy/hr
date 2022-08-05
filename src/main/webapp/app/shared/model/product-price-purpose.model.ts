export interface IProductPricePurpose {
  id?: number;
  name?: string;
  description?: string;
}

export class ProductPricePurpose implements IProductPricePurpose {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
