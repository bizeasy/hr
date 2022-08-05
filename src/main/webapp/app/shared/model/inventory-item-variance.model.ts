import { IInventoryItem } from 'app/shared/model/inventory-item.model';
import { IReason } from 'app/shared/model/reason.model';

export interface IInventoryItemVariance {
  id?: number;
  varianceReason?: string;
  atpVar?: number;
  qohVar?: number;
  comments?: string;
  inventoryItem?: IInventoryItem;
  reason?: IReason;
}

export class InventoryItemVariance implements IInventoryItemVariance {
  constructor(
    public id?: number,
    public varianceReason?: string,
    public atpVar?: number,
    public qohVar?: number,
    public comments?: string,
    public inventoryItem?: IInventoryItem,
    public reason?: IReason
  ) {}
}
