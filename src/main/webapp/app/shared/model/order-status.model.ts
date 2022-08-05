import { Moment } from 'moment';
import { IStatus } from 'app/shared/model/status.model';
import { IOrder } from 'app/shared/model/order.model';
import { IReason } from 'app/shared/model/reason.model';

export interface IOrderStatus {
  id?: number;
  statusDateTime?: Moment;
  sequenceNo?: number;
  status?: IStatus;
  order?: IOrder;
  reason?: IReason;
}

export class OrderStatus implements IOrderStatus {
  constructor(
    public id?: number,
    public statusDateTime?: Moment,
    public sequenceNo?: number,
    public status?: IStatus,
    public order?: IOrder,
    public reason?: IReason
  ) {}
}
