export interface IInterviewGrade {
  id?: number;
  name?: string;
  description?: string;
}

export class InterviewGrade implements IInterviewGrade {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
