export interface IEmplPositionGroup {
  id?: number;
  name?: string;
  description?: string;
}

export class EmplPositionGroup implements IEmplPositionGroup {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
