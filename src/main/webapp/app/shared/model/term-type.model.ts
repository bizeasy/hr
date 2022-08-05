export interface ITermType {
  id?: number;
  name?: string;
  description?: string;
}

export class TermType implements ITermType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
