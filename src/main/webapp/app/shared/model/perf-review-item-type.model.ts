export interface IPerfReviewItemType {
  id?: number;
  name?: string;
  description?: string;
}

export class PerfReviewItemType implements IPerfReviewItemType {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
