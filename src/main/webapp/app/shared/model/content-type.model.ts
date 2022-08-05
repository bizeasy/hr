export interface IContentType {
  id?: number;
  name?: string;
}

export class ContentType implements IContentType {
  constructor(public id?: number, public name?: string) {}
}
