import { IProduct } from 'app/shared/model/product.model';
import { IFacility } from 'app/shared/model/facility.model';

export interface IProductFacility {
  id?: number;
  minimumStock?: number;
  reorderQty?: number;
  daysToShip?: number;
  lastInventoryCount?: number;
  product?: IProduct;
  facility?: IFacility;
}

export class ProductFacility implements IProductFacility {
  constructor(
    public id?: number,
    public minimumStock?: number,
    public reorderQty?: number,
    public daysToShip?: number,
    public lastInventoryCount?: number,
    public product?: IProduct,
    public facility?: IFacility
  ) {}
}
