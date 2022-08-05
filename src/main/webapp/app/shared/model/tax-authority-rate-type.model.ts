import { ITaxAuthority } from 'app/shared/model/tax-authority.model';
import { ITaxSlab } from 'app/shared/model/tax-slab.model';

export interface ITaxAuthorityRateType {
  id?: number;
  name?: string;
  description?: string;
  taxAuthority?: ITaxAuthority;
  taxSlab?: ITaxSlab;
}

export class TaxAuthorityRateType implements ITaxAuthorityRateType {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public taxAuthority?: ITaxAuthority,
    public taxSlab?: ITaxSlab
  ) {}
}
