import { Moment } from 'moment';
import { IReason } from 'app/shared/model/reason.model';
import { ITerminationType } from 'app/shared/model/termination-type.model';
import { IParty } from 'app/shared/model/party.model';
import { IRoleType } from 'app/shared/model/role-type.model';

export interface IEmployment {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  terminationReason?: IReason;
  terminationType?: ITerminationType;
  employee?: IParty;
  fromParty?: IParty;
  roleTypeFrom?: IRoleType;
  roleTypeTo?: IParty;
}

export class Employment implements IEmployment {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public terminationReason?: IReason,
    public terminationType?: ITerminationType,
    public employee?: IParty,
    public fromParty?: IParty,
    public roleTypeFrom?: IRoleType,
    public roleTypeTo?: IParty
  ) {}
}
