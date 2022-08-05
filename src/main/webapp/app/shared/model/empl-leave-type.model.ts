export interface IEmplLeaveType {
  id?: number;
  name?: string;
  description?: string;
}

export class EmplLeaveType implements IEmplLeaveType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
