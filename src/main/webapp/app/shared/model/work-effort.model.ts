import { IProduct } from 'app/shared/model/product.model';
import { IWorkEffortType } from 'app/shared/model/work-effort-type.model';
import { IWorkEffortPurpose } from 'app/shared/model/work-effort-purpose.model';
import { IStatus } from 'app/shared/model/status.model';
import { IFacility } from 'app/shared/model/facility.model';
import { IUom } from 'app/shared/model/uom.model';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { IProductStore } from 'app/shared/model/product-store.model';

export interface IWorkEffort {
  id?: number;
  name?: string;
  description?: string;
  code?: string;
  batchSize?: number;
  minYieldRange?: number;
  maxYieldRange?: number;
  percentComplete?: number;
  validationType?: string;
  shelfLife?: number;
  outputQty?: number;
  product?: IProduct;
  ksm?: IProduct;
  type?: IWorkEffortType;
  purpose?: IWorkEffortPurpose;
  status?: IStatus;
  facility?: IFacility;
  shelfListUom?: IUom;
  productCategory?: IProductCategory;
  productStore?: IProductStore;
}

export class WorkEffort implements IWorkEffort {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public code?: string,
    public batchSize?: number,
    public minYieldRange?: number,
    public maxYieldRange?: number,
    public percentComplete?: number,
    public validationType?: string,
    public shelfLife?: number,
    public outputQty?: number,
    public product?: IProduct,
    public ksm?: IProduct,
    public type?: IWorkEffortType,
    public purpose?: IWorkEffortPurpose,
    public status?: IStatus,
    public facility?: IFacility,
    public shelfListUom?: IUom,
    public productCategory?: IProductCategory,
    public productStore?: IProductStore
  ) {}
}
