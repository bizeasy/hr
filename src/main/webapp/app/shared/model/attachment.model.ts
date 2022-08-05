export interface IAttachment {
  id?: number;
  name?: string;
  fileAttachmentContentType?: string;
  fileAttachment?: any;
  attachmentUrl?: string;
  mimeType?: string;
}

export class Attachment implements IAttachment {
  constructor(
    public id?: number,
    public name?: string,
    public fileAttachmentContentType?: string,
    public fileAttachment?: any,
    public attachmentUrl?: string,
    public mimeType?: string
  ) {}
}
