import { IGeo } from 'app/shared/model/geo.model';
import { IContactMech } from 'app/shared/model/contact-mech.model';
import { IGeoPoint } from 'app/shared/model/geo-point.model';

export interface IPostalAddress {
  id?: number;
  toName?: string;
  address1?: string;
  address2?: string;
  city?: string;
  landmark?: string;
  postalCode?: string;
  isDefault?: boolean;
  customAddressType?: string;
  stateStr?: string;
  countryStr?: string;
  isIndianAddress?: boolean;
  note?: string;
  directions?: string;
  state?: IGeo;
  pincode?: IGeo;
  country?: IGeo;
  contactMech?: IContactMech;
  geoPoint?: IGeoPoint;
}

export class PostalAddress implements IPostalAddress {
  constructor(
    public id?: number,
    public toName?: string,
    public address1?: string,
    public address2?: string,
    public city?: string,
    public landmark?: string,
    public postalCode?: string,
    public isDefault?: boolean,
    public customAddressType?: string,
    public stateStr?: string,
    public countryStr?: string,
    public isIndianAddress?: boolean,
    public note?: string,
    public directions?: string,
    public state?: IGeo,
    public pincode?: IGeo,
    public country?: IGeo,
    public contactMech?: IContactMech,
    public geoPoint?: IGeoPoint
  ) {
    this.isDefault = this.isDefault || false;
    this.isIndianAddress = this.isIndianAddress || false;
  }
}
