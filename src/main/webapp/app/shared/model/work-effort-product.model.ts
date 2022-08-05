import { IWorkEffort } from 'app/shared/model/work-effort.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IWorkEffortProduct {
  id?: number;
  sequenceNo?: number;
  quantity?: number;
  workEffort?: IWorkEffort;
  product?: IProduct;
}

export class WorkEffortProduct implements IWorkEffortProduct {
  constructor(
    public id?: number,
    public sequenceNo?: number,
    public quantity?: number,
    public workEffort?: IWorkEffort,
    public product?: IProduct
  ) {}
}
