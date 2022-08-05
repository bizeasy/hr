export interface IContactMechType {
  id?: number;
  name?: string;
  description?: string;
}

export class ContactMechType implements IContactMechType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
