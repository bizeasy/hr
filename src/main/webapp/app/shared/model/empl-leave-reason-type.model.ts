export interface IEmplLeaveReasonType {
  id?: number;
  name?: string;
  description?: string;
}

export class EmplLeaveReasonType implements IEmplLeaveReasonType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
