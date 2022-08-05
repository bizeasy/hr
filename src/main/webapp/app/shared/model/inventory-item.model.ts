import { Moment } from 'moment';
import { IInventoryItemType } from 'app/shared/model/inventory-item-type.model';
import { IProduct } from 'app/shared/model/product.model';
import { IParty } from 'app/shared/model/party.model';
import { IStatus } from 'app/shared/model/status.model';
import { IFacility } from 'app/shared/model/facility.model';
import { IUom } from 'app/shared/model/uom.model';
import { ILot } from 'app/shared/model/lot.model';

export interface IInventoryItem {
  id?: number;
  receivedDate?: Moment;
  manufacturedDate?: Moment;
  expiryDate?: Moment;
  retestDate?: Moment;
  containerId?: string;
  batchNo?: string;
  mfgBatchNo?: string;
  lotNoStr?: string;
  binNumber?: string;
  comments?: string;
  quantityOnHandTotal?: number;
  availableToPromiseTotal?: number;
  accountingQuantityTotal?: number;
  oldQuantityOnHand?: number;
  oldAvailableToPromise?: number;
  serialNumber?: string;
  softIdentifier?: string;
  activationNumber?: string;
  activationValidTrue?: Moment;
  unitCost?: number;
  inventoryItemType?: IInventoryItemType;
  product?: IProduct;
  supplier?: IParty;
  ownerParty?: IParty;
  status?: IStatus;
  facility?: IFacility;
  uom?: IUom;
  currencyUom?: IUom;
  lot?: ILot;
}

export class InventoryItem implements IInventoryItem {
  constructor(
    public id?: number,
    public receivedDate?: Moment,
    public manufacturedDate?: Moment,
    public expiryDate?: Moment,
    public retestDate?: Moment,
    public containerId?: string,
    public batchNo?: string,
    public mfgBatchNo?: string,
    public lotNoStr?: string,
    public binNumber?: string,
    public comments?: string,
    public quantityOnHandTotal?: number,
    public availableToPromiseTotal?: number,
    public accountingQuantityTotal?: number,
    public oldQuantityOnHand?: number,
    public oldAvailableToPromise?: number,
    public serialNumber?: string,
    public softIdentifier?: string,
    public activationNumber?: string,
    public activationValidTrue?: Moment,
    public unitCost?: number,
    public inventoryItemType?: IInventoryItemType,
    public product?: IProduct,
    public supplier?: IParty,
    public ownerParty?: IParty,
    public status?: IStatus,
    public facility?: IFacility,
    public uom?: IUom,
    public currencyUom?: IUom,
    public lot?: ILot
  ) {}
}
