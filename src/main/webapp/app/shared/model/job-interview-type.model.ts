export interface IJobInterviewType {
  id?: number;
  name?: string;
  description?: string;
}

export class JobInterviewType implements IJobInterviewType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
