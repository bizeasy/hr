import { Moment } from 'moment';
import { IQualification } from 'app/shared/model/qualification.model';
import { ISkillType } from 'app/shared/model/skill-type.model';
import { IGeo } from 'app/shared/model/geo.model';
import { IExamType } from 'app/shared/model/exam-type.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IJobRequisition {
  id?: number;
  duration?: number;
  age?: number;
  gender?: Gender;
  experienceMonths?: number;
  experienceYears?: number;
  qualificationStr?: string;
  noOfResource?: number;
  requiredOnDate?: Moment;
  qualificiation?: IQualification;
  skillType?: ISkillType;
  jobLocation?: IGeo;
  examType?: IExamType;
}

export class JobRequisition implements IJobRequisition {
  constructor(
    public id?: number,
    public duration?: number,
    public age?: number,
    public gender?: Gender,
    public experienceMonths?: number,
    public experienceYears?: number,
    public qualificationStr?: string,
    public noOfResource?: number,
    public requiredOnDate?: Moment,
    public qualificiation?: IQualification,
    public skillType?: ISkillType,
    public jobLocation?: IGeo,
    public examType?: IExamType
  ) {}
}
