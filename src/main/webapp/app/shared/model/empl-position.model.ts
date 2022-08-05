import { Moment } from 'moment';
import { IEmplPositionType } from 'app/shared/model/empl-position-type.model';
import { IParty } from 'app/shared/model/party.model';
import { IStatus } from 'app/shared/model/status.model';

export interface IEmplPosition {
  id?: number;
  maxBudget?: number;
  minBudget?: number;
  estimatedFromDate?: Moment;
  estimatedThruDate?: Moment;
  paidJob?: boolean;
  isFulltime?: boolean;
  isTemporary?: boolean;
  actualFromDate?: Moment;
  actualThruDate?: Moment;
  type?: IEmplPositionType;
  party?: IParty;
  status?: IStatus;
}

export class EmplPosition implements IEmplPosition {
  constructor(
    public id?: number,
    public maxBudget?: number,
    public minBudget?: number,
    public estimatedFromDate?: Moment,
    public estimatedThruDate?: Moment,
    public paidJob?: boolean,
    public isFulltime?: boolean,
    public isTemporary?: boolean,
    public actualFromDate?: Moment,
    public actualThruDate?: Moment,
    public type?: IEmplPositionType,
    public party?: IParty,
    public status?: IStatus
  ) {
    this.paidJob = this.paidJob || false;
    this.isFulltime = this.isFulltime || false;
    this.isTemporary = this.isTemporary || false;
  }
}
