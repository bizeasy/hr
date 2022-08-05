import { Moment } from 'moment';

export interface ILot {
  id?: number;
  creationDate?: Moment;
  quantity?: number;
  expirationDate?: Moment;
  retestDate?: Moment;
}

export class Lot implements ILot {
  constructor(
    public id?: number,
    public creationDate?: Moment,
    public quantity?: number,
    public expirationDate?: Moment,
    public retestDate?: Moment
  ) {}
}
