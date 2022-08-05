import { IFacilityGroupType } from 'app/shared/model/facility-group-type.model';

export interface IFacilityGroup {
  id?: number;
  name?: string;
  description?: string;
  facilityGroupType?: IFacilityGroupType;
  facilityGroup?: IFacilityGroup;
}

export class FacilityGroup implements IFacilityGroup {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public facilityGroupType?: IFacilityGroupType,
    public facilityGroup?: IFacilityGroup
  ) {}
}
