import { IFacilityType } from 'app/shared/model/facility-type.model';
import { IParty } from 'app/shared/model/party.model';
import { IFacilityGroup } from 'app/shared/model/facility-group.model';
import { IUom } from 'app/shared/model/uom.model';
import { IGeoPoint } from 'app/shared/model/geo-point.model';
import { IInventoryItemType } from 'app/shared/model/inventory-item-type.model';
import { IStatus } from 'app/shared/model/status.model';
import { IUser } from 'app/core/user/user.model';

export interface IFacility {
  id?: number;
  name?: string;
  description?: string;
  facilityCode?: string;
  facilitySize?: number;
  costCenterCode?: string;
  facilityType?: IFacilityType;
  parentFacility?: IFacility;
  ownerParty?: IParty;
  facilityGroup?: IFacilityGroup;
  sizeUom?: IUom;
  geoPoint?: IGeoPoint;
  inventoryItemType?: IInventoryItemType;
  status?: IStatus;
  usageStatus?: IStatus;
  reviewedBy?: IUser;
  approvedBy?: IUser;
}

export class Facility implements IFacility {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public facilityCode?: string,
    public facilitySize?: number,
    public costCenterCode?: string,
    public facilityType?: IFacilityType,
    public parentFacility?: IFacility,
    public ownerParty?: IParty,
    public facilityGroup?: IFacilityGroup,
    public sizeUom?: IUom,
    public geoPoint?: IGeoPoint,
    public inventoryItemType?: IInventoryItemType,
    public status?: IStatus,
    public usageStatus?: IStatus,
    public reviewedBy?: IUser,
    public approvedBy?: IUser
  ) {}
}
