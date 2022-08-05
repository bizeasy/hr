import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { TaxAuthorityRateTypeComponent } from './tax-authority-rate-type.component';
import { TaxAuthorityRateTypeDetailComponent } from './tax-authority-rate-type-detail.component';
import { TaxAuthorityRateTypeUpdateComponent } from './tax-authority-rate-type-update.component';
import { TaxAuthorityRateTypeDeleteDialogComponent } from './tax-authority-rate-type-delete-dialog.component';
import { taxAuthorityRateTypeRoute } from './tax-authority-rate-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(taxAuthorityRateTypeRoute)],
  declarations: [
    TaxAuthorityRateTypeComponent,
    TaxAuthorityRateTypeDetailComponent,
    TaxAuthorityRateTypeUpdateComponent,
    TaxAuthorityRateTypeDeleteDialogComponent,
  ],
  entryComponents: [TaxAuthorityRateTypeDeleteDialogComponent],
})
export class HrTaxAuthorityRateTypeModule {}
