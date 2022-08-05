import { Moment } from 'moment';
import { IInvoiceType } from 'app/shared/model/invoice-type.model';
import { IParty } from 'app/shared/model/party.model';
import { IRoleType } from 'app/shared/model/role-type.model';
import { IStatus } from 'app/shared/model/status.model';
import { IContactMech } from 'app/shared/model/contact-mech.model';

export interface IInvoice {
  id?: number;
  invoiceDate?: Moment;
  dueDate?: Moment;
  paidDate?: Moment;
  invoiceMessage?: string;
  referenceNumber?: string;
  invoiceType?: IInvoiceType;
  partyIdFrom?: IParty;
  partyIdTo?: IParty;
  roleType?: IRoleType;
  status?: IStatus;
  contactMech?: IContactMech;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public invoiceDate?: Moment,
    public dueDate?: Moment,
    public paidDate?: Moment,
    public invoiceMessage?: string,
    public referenceNumber?: string,
    public invoiceType?: IInvoiceType,
    public partyIdFrom?: IParty,
    public partyIdTo?: IParty,
    public roleType?: IRoleType,
    public status?: IStatus,
    public contactMech?: IContactMech
  ) {}
}
