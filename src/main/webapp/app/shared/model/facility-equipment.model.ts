import { IFacility } from 'app/shared/model/facility.model';
import { IEquipment } from 'app/shared/model/equipment.model';

export interface IFacilityEquipment {
  id?: number;
  name?: string;
  description?: string;
  facility?: IFacility;
  equipment?: IEquipment;
}

export class FacilityEquipment implements IFacilityEquipment {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public facility?: IFacility,
    public equipment?: IEquipment
  ) {}
}
