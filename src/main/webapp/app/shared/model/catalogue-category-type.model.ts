export interface ICatalogueCategoryType {
  id?: number;
  name?: string;
  description?: string;
}

export class CatalogueCategoryType implements ICatalogueCategoryType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
