export interface ITerminationType {
  id?: number;
  name?: string;
  description?: string;
}

export class TerminationType implements ITerminationType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
