import { IWorkEffort } from 'app/shared/model/work-effort.model';
import { IInventoryItem } from 'app/shared/model/inventory-item.model';
import { IStatus } from 'app/shared/model/status.model';

export interface IWorkEffortInventoryAssign {
  id?: number;
  quantity?: number;
  workEffort?: IWorkEffort;
  inventoryItem?: IInventoryItem;
  status?: IStatus;
}

export class WorkEffortInventoryAssign implements IWorkEffortInventoryAssign {
  constructor(
    public id?: number,
    public quantity?: number,
    public workEffort?: IWorkEffort,
    public inventoryItem?: IInventoryItem,
    public status?: IStatus
  ) {}
}
