import { Moment } from 'moment';
import { IParty } from 'app/shared/model/party.model';
import { IEmplPosition } from 'app/shared/model/empl-position.model';

export interface IPerfReview {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  comments?: string;
  employee?: IParty;
  manager?: IParty;
  emplPosition?: IEmplPosition;
}

export class PerfReview implements IPerfReview {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public comments?: string,
    public employee?: IParty,
    public manager?: IParty,
    public emplPosition?: IEmplPosition
  ) {}
}
