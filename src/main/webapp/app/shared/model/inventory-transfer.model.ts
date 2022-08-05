import { Moment } from 'moment';
import { IStatus } from 'app/shared/model/status.model';
import { IInventoryItem } from 'app/shared/model/inventory-item.model';
import { IFacility } from 'app/shared/model/facility.model';
import { IItemIssuance } from 'app/shared/model/item-issuance.model';

export interface IInventoryTransfer {
  id?: number;
  sentDate?: Moment;
  receivedDate?: Moment;
  comments?: string;
  status?: IStatus;
  inventoryItem?: IInventoryItem;
  facility?: IFacility;
  facilityTo?: IFacility;
  issuance?: IItemIssuance;
}

export class InventoryTransfer implements IInventoryTransfer {
  constructor(
    public id?: number,
    public sentDate?: Moment,
    public receivedDate?: Moment,
    public comments?: string,
    public status?: IStatus,
    public inventoryItem?: IInventoryItem,
    public facility?: IFacility,
    public facilityTo?: IFacility,
    public issuance?: IItemIssuance
  ) {}
}
