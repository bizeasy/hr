export interface ISalesChannel {
  id?: number;
  name?: string;
  description?: string;
  sequenceNo?: number;
}

export class SalesChannel implements ISalesChannel {
  constructor(public id?: number, public name?: string, public description?: string, public sequenceNo?: number) {}
}
