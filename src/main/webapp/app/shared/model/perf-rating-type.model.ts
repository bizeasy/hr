export interface IPerfRatingType {
  id?: number;
  name?: string;
  description?: string;
}

export class PerfRatingType implements IPerfRatingType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
