import { Moment } from 'moment';
import { ICommunicationEventType } from 'app/shared/model/communication-event-type.model';
import { IStatus } from 'app/shared/model/status.model';
import { IContactMechType } from 'app/shared/model/contact-mech-type.model';
import { IContactMech } from 'app/shared/model/contact-mech.model';
import { IParty } from 'app/shared/model/party.model';

export interface ICommunicationEvent {
  id?: number;
  entryDate?: Moment;
  subject?: string;
  content?: string;
  fromString?: string;
  toString?: string;
  ccString?: string;
  message?: string;
  dateStarted?: Moment;
  dateEnded?: Moment;
  info?: any;
  communicationEventType?: ICommunicationEventType;
  status?: IStatus;
  contactMechType?: IContactMechType;
  contactMechFrom?: IContactMech;
  contactMechTo?: IContactMech;
  fromParty?: IParty;
  toParty?: IParty;
}

export class CommunicationEvent implements ICommunicationEvent {
  constructor(
    public id?: number,
    public entryDate?: Moment,
    public subject?: string,
    public content?: string,
    public fromString?: string,
    public toString?: string,
    public ccString?: string,
    public message?: string,
    public dateStarted?: Moment,
    public dateEnded?: Moment,
    public info?: any,
    public communicationEventType?: ICommunicationEventType,
    public status?: IStatus,
    public contactMechType?: IContactMechType,
    public contactMechFrom?: IContactMech,
    public contactMechTo?: IContactMech,
    public fromParty?: IParty,
    public toParty?: IParty
  ) {}
}
