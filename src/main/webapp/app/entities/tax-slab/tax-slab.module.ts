import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { TaxSlabComponent } from './tax-slab.component';
import { TaxSlabDetailComponent } from './tax-slab-detail.component';
import { TaxSlabUpdateComponent } from './tax-slab-update.component';
import { TaxSlabDeleteDialogComponent } from './tax-slab-delete-dialog.component';
import { taxSlabRoute } from './tax-slab.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(taxSlabRoute)],
  declarations: [TaxSlabComponent, TaxSlabDetailComponent, TaxSlabUpdateComponent, TaxSlabDeleteDialogComponent],
  entryComponents: [TaxSlabDeleteDialogComponent],
})
export class HrTaxSlabModule {}
