import { IUserGroup } from 'app/shared/model/user-group.model';
import { IUser } from 'app/core/user/user.model';
import { IParty } from 'app/shared/model/party.model';
import { IProductStore } from 'app/shared/model/product-store.model';

export interface IProductStoreUserGroup {
  id?: number;
  userGroup?: IUserGroup;
  user?: IUser;
  party?: IParty;
  productStore?: IProductStore;
}

export class ProductStoreUserGroup implements IProductStoreUserGroup {
  constructor(
    public id?: number,
    public userGroup?: IUserGroup,
    public user?: IUser,
    public party?: IParty,
    public productStore?: IProductStore
  ) {}
}
