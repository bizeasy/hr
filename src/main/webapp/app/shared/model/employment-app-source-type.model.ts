export interface IEmploymentAppSourceType {
  id?: number;
  name?: string;
  description?: string;
}

export class EmploymentAppSourceType implements IEmploymentAppSourceType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
