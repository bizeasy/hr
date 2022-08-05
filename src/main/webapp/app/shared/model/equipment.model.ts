import { Moment } from 'moment';
import { IEquipmentType } from 'app/shared/model/equipment-type.model';
import { IStatus } from 'app/shared/model/status.model';

export interface IEquipment {
  id?: number;
  name?: string;
  description?: string;
  equipmentNumber?: string;
  minCapacityRange?: number;
  maxCapacityRange?: number;
  minOperationalRange?: number;
  maxOperationalRange?: number;
  lastCalibratedDate?: Moment;
  calibrationDueDate?: Moment;
  changeControlNo?: string;
  equipmentType?: IEquipmentType;
  status?: IStatus;
}

export class Equipment implements IEquipment {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public equipmentNumber?: string,
    public minCapacityRange?: number,
    public maxCapacityRange?: number,
    public minOperationalRange?: number,
    public maxOperationalRange?: number,
    public lastCalibratedDate?: Moment,
    public calibrationDueDate?: Moment,
    public changeControlNo?: string,
    public equipmentType?: IEquipmentType,
    public status?: IStatus
  ) {}
}
