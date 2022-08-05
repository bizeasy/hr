export interface IInventoryItemType {
  id?: number;
  name?: string;
  description?: string;
}

export class InventoryItemType implements IInventoryItemType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
