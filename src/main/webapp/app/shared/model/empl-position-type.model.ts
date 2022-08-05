import { IEmplPositionGroup } from 'app/shared/model/empl-position-group.model';

export interface IEmplPositionType {
  id?: number;
  name?: string;
  description?: string;
  group?: IEmplPositionGroup;
}

export class EmplPositionType implements IEmplPositionType {
  constructor(public id?: number, public name?: string, public description?: string, public group?: IEmplPositionGroup) {}
}
