import { Moment } from 'moment';
import { IParty } from 'app/shared/model/party.model';
import { IJobInterviewType } from 'app/shared/model/job-interview-type.model';
import { IJobRequisition } from 'app/shared/model/job-requisition.model';
import { IInterviewGrade } from 'app/shared/model/interview-grade.model';
import { IInterviewResult } from 'app/shared/model/interview-result.model';

export interface IJobInterview {
  id?: number;
  remarks?: string;
  interviewDate?: Moment;
  interviewee?: IParty;
  interviewer?: IParty;
  type?: IJobInterviewType;
  jobRequisition?: IJobRequisition;
  gradeSecured?: IInterviewGrade;
  result?: IInterviewResult;
}

export class JobInterview implements IJobInterview {
  constructor(
    public id?: number,
    public remarks?: string,
    public interviewDate?: Moment,
    public interviewee?: IParty,
    public interviewer?: IParty,
    public type?: IJobInterviewType,
    public jobRequisition?: IJobRequisition,
    public gradeSecured?: IInterviewGrade,
    public result?: IInterviewResult
  ) {}
}
