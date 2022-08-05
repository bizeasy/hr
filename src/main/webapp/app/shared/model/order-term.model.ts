import { IOrder } from 'app/shared/model/order.model';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { ITerm } from 'app/shared/model/term.model';
import { IOrderTermType } from 'app/shared/model/order-term-type.model';

export interface IOrderTerm {
  id?: number;
  sequenceNo?: number;
  name?: string;
  detail?: string;
  termValue?: number;
  termDays?: number;
  textValue?: string;
  order?: IOrder;
  item?: IOrderItem;
  term?: ITerm;
  type?: IOrderTermType;
}

export class OrderTerm implements IOrderTerm {
  constructor(
    public id?: number,
    public sequenceNo?: number,
    public name?: string,
    public detail?: string,
    public termValue?: number,
    public termDays?: number,
    public textValue?: string,
    public order?: IOrder,
    public item?: IOrderItem,
    public term?: ITerm,
    public type?: IOrderTermType
  ) {}
}
