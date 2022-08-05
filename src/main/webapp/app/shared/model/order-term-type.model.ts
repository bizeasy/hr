export interface IOrderTermType {
  id?: number;
  name?: string;
  description?: string;
}

export class OrderTermType implements IOrderTermType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
