import { Moment } from 'moment';
import { IEmplPosition } from 'app/shared/model/empl-position.model';
import { IParty } from 'app/shared/model/party.model';

export interface IEmplPositionFulfillment {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  comments?: string;
  emplPosition?: IEmplPosition;
  party?: IParty;
  reportingTo?: IParty;
  managedBy?: IParty;
}

export class EmplPositionFulfillment implements IEmplPositionFulfillment {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public comments?: string,
    public emplPosition?: IEmplPosition,
    public party?: IParty,
    public reportingTo?: IParty,
    public managedBy?: IParty
  ) {}
}
