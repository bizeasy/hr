import { Moment } from 'moment';
import { IOrder } from 'app/shared/model/order.model';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { IInventoryItem } from 'app/shared/model/inventory-item.model';
import { IUser } from 'app/core/user/user.model';
import { IReason } from 'app/shared/model/reason.model';
import { IFacility } from 'app/shared/model/facility.model';
import { IStatus } from 'app/shared/model/status.model';

export interface IItemIssuance {
  id?: number;
  message?: string;
  issuedDate?: Moment;
  issuedBy?: string;
  quantity?: number;
  cancelQuantity?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  equipmentId?: string;
  order?: IOrder;
  orderItem?: IOrderItem;
  inventoryItem?: IInventoryItem;
  issuedByUserLogin?: IUser;
  varianceReason?: IReason;
  facility?: IFacility;
  status?: IStatus;
}

export class ItemIssuance implements IItemIssuance {
  constructor(
    public id?: number,
    public message?: string,
    public issuedDate?: Moment,
    public issuedBy?: string,
    public quantity?: number,
    public cancelQuantity?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public equipmentId?: string,
    public order?: IOrder,
    public orderItem?: IOrderItem,
    public inventoryItem?: IInventoryItem,
    public issuedByUserLogin?: IUser,
    public varianceReason?: IReason,
    public facility?: IFacility,
    public status?: IStatus
  ) {}
}
