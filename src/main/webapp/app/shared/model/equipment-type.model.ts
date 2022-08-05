export interface IEquipmentType {
  id?: number;
  name?: string;
  description?: string;
}

export class EquipmentType implements IEquipmentType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
