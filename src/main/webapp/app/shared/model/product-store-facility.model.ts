import { Moment } from 'moment';
import { IProductStore } from 'app/shared/model/product-store.model';
import { IFacility } from 'app/shared/model/facility.model';

export interface IProductStoreFacility {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  sequenceNo?: number;
  productStore?: IProductStore;
  facility?: IFacility;
}

export class ProductStoreFacility implements IProductStoreFacility {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public sequenceNo?: number,
    public productStore?: IProductStore,
    public facility?: IFacility
  ) {}
}
