import { Moment } from 'moment';
import { IParty } from 'app/shared/model/party.model';

export interface IPartyResume {
  id?: number;
  text?: string;
  resumeDate?: Moment;
  fileAttachmentContentType?: string;
  fileAttachment?: any;
  attachmentUrl?: string;
  mimeType?: string;
  party?: IParty;
}

export class PartyResume implements IPartyResume {
  constructor(
    public id?: number,
    public text?: string,
    public resumeDate?: Moment,
    public fileAttachmentContentType?: string,
    public fileAttachment?: any,
    public attachmentUrl?: string,
    public mimeType?: string,
    public party?: IParty
  ) {}
}
