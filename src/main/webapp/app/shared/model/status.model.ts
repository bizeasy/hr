import { IStatusCategory } from 'app/shared/model/status-category.model';

export interface IStatus {
  id?: number;
  name?: string;
  sequenceNo?: number;
  description?: string;
  action?: string;
  category?: IStatusCategory;
}

export class Status implements IStatus {
  constructor(
    public id?: number,
    public name?: string,
    public sequenceNo?: number,
    public description?: string,
    public action?: string,
    public category?: IStatusCategory
  ) {}
}
