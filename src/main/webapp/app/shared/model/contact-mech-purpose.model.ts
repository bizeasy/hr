export interface IContactMechPurpose {
  id?: number;
  name?: string;
  description?: string;
}

export class ContactMechPurpose implements IContactMechPurpose {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
