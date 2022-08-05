export interface ITaxAuthority {
  id?: number;
  name?: string;
}

export class TaxAuthority implements ITaxAuthority {
  constructor(public id?: number, public name?: string) {}
}
