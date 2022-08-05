import { ICatalogue } from 'app/shared/model/catalogue.model';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ICatalogueCategoryType } from 'app/shared/model/catalogue-category-type.model';

export interface ICatalogueCategory {
  id?: number;
  sequenceNo?: number;
  catalogue?: ICatalogue;
  productCategory?: IProductCategory;
  catalogueCategoryType?: ICatalogueCategoryType;
}

export class CatalogueCategory implements ICatalogueCategory {
  constructor(
    public id?: number,
    public sequenceNo?: number,
    public catalogue?: ICatalogue,
    public productCategory?: IProductCategory,
    public catalogueCategoryType?: ICatalogueCategoryType
  ) {}
}
