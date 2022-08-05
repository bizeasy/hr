export interface IKeywordType {
  id?: number;
  name?: string;
}

export class KeywordType implements IKeywordType {
  constructor(public id?: number, public name?: string) {}
}
