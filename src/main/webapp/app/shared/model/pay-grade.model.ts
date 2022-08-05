export interface IPayGrade {
  id?: number;
  name?: string;
  description?: string;
}

export class PayGrade implements IPayGrade {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
