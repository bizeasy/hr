import { IContactMechType } from 'app/shared/model/contact-mech-type.model';

export interface ICommunicationEventType {
  id?: number;
  name?: string;
  description?: string;
  contactMechType?: IContactMechType;
}

export class CommunicationEventType implements ICommunicationEventType {
  constructor(public id?: number, public name?: string, public description?: string, public contactMechType?: IContactMechType) {}
}
