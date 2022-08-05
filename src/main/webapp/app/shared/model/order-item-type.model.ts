export interface IOrderItemType {
  id?: number;
  name?: string;
  description?: string;
}

export class OrderItemType implements IOrderItemType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
