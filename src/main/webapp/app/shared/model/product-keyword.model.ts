import { IProduct } from 'app/shared/model/product.model';
import { IKeywordType } from 'app/shared/model/keyword-type.model';

export interface IProductKeyword {
  id?: number;
  keyword?: string;
  relevancyWeight?: number;
  product?: IProduct;
  keywordType?: IKeywordType;
}

export class ProductKeyword implements IProductKeyword {
  constructor(
    public id?: number,
    public keyword?: string,
    public relevancyWeight?: number,
    public product?: IProduct,
    public keywordType?: IKeywordType
  ) {}
}
