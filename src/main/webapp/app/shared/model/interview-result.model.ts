export interface IInterviewResult {
  id?: number;
  name?: string;
  description?: string;
}

export class InterviewResult implements IInterviewResult {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
