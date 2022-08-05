import { IContactMechType } from 'app/shared/model/contact-mech-type.model';

export interface IContactMech {
  id?: number;
  infoString?: string;
  contactMechType?: IContactMechType;
}

export class ContactMech implements IContactMech {
  constructor(public id?: number, public infoString?: string, public contactMechType?: IContactMechType) {}
}
