export interface ISkillType {
  id?: number;
  name?: string;
  description?: string;
}

export class SkillType implements ISkillType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
