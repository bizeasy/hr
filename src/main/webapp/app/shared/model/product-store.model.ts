import { IProductStoreType } from 'app/shared/model/product-store-type.model';
import { IParty } from 'app/shared/model/party.model';
import { IPostalAddress } from 'app/shared/model/postal-address.model';

export interface IProductStore {
  id?: number;
  name?: string;
  title?: string;
  subtitle?: string;
  imageUrl?: string;
  comments?: string;
  code?: string;
  type?: IProductStoreType;
  parent?: IProductStore;
  owner?: IParty;
  postalAddress?: IPostalAddress;
}

export class ProductStore implements IProductStore {
  constructor(
    public id?: number,
    public name?: string,
    public title?: string,
    public subtitle?: string,
    public imageUrl?: string,
    public comments?: string,
    public code?: string,
    public type?: IProductStoreType,
    public parent?: IProductStore,
    public owner?: IParty,
    public postalAddress?: IPostalAddress
  ) {}
}
