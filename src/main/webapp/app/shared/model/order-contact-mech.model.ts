import { Moment } from 'moment';
import { IOrder } from 'app/shared/model/order.model';
import { IContactMech } from 'app/shared/model/contact-mech.model';
import { IContactMechPurpose } from 'app/shared/model/contact-mech-purpose.model';

export interface IOrderContactMech {
  id?: number;
  fromDate?: Moment;
  thruDate?: Moment;
  order?: IOrder;
  contactMech?: IContactMech;
  contactMechPurpose?: IContactMechPurpose;
}

export class OrderContactMech implements IOrderContactMech {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public thruDate?: Moment,
    public order?: IOrder,
    public contactMech?: IContactMech,
    public contactMechPurpose?: IContactMechPurpose
  ) {}
}
