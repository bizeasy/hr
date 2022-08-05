export interface IOrderType {
  id?: number;
  name?: string;
  description?: string;
}

export class OrderType implements IOrderType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
