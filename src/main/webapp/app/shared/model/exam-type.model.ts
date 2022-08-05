export interface IExamType {
  id?: number;
  name?: string;
  description?: string;
}

export class ExamType implements IExamType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
