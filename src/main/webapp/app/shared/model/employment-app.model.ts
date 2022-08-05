import { Moment } from 'moment';
import { IEmplPosition } from 'app/shared/model/empl-position.model';
import { IStatus } from 'app/shared/model/status.model';
import { IEmploymentAppSourceType } from 'app/shared/model/employment-app-source-type.model';
import { IParty } from 'app/shared/model/party.model';
import { IJobRequisition } from 'app/shared/model/job-requisition.model';

export interface IEmploymentApp {
  id?: number;
  applicationDate?: Moment;
  emplPosition?: IEmplPosition;
  status?: IStatus;
  source?: IEmploymentAppSourceType;
  applyingParty?: IParty;
  referredByParty?: IParty;
  approverParty?: IParty;
  jobRequisition?: IJobRequisition;
}

export class EmploymentApp implements IEmploymentApp {
  constructor(
    public id?: number,
    public applicationDate?: Moment,
    public emplPosition?: IEmplPosition,
    public status?: IStatus,
    public source?: IEmploymentAppSourceType,
    public applyingParty?: IParty,
    public referredByParty?: IParty,
    public approverParty?: IParty,
    public jobRequisition?: IJobRequisition
  ) {}
}
