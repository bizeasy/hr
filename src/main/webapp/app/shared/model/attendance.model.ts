import { Moment } from 'moment';
import { IParty } from 'app/shared/model/party.model';

export interface IAttendance {
  id?: number;
  punchIn?: Moment;
  punchOut?: Moment;
  employee?: IParty;
}

export class Attendance implements IAttendance {
  constructor(public id?: number, public punchIn?: Moment, public punchOut?: Moment, public employee?: IParty) {}
}
