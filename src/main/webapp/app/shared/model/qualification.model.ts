export interface IQualification {
  id?: number;
  name?: string;
  description?: string;
}

export class Qualification implements IQualification {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
