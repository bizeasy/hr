import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { TaxAuthorityComponent } from './tax-authority.component';
import { TaxAuthorityDetailComponent } from './tax-authority-detail.component';
import { TaxAuthorityUpdateComponent } from './tax-authority-update.component';
import { TaxAuthorityDeleteDialogComponent } from './tax-authority-delete-dialog.component';
import { taxAuthorityRoute } from './tax-authority.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(taxAuthorityRoute)],
  declarations: [TaxAuthorityComponent, TaxAuthorityDetailComponent, TaxAuthorityUpdateComponent, TaxAuthorityDeleteDialogComponent],
  entryComponents: [TaxAuthorityDeleteDialogComponent],
})
export class HrTaxAuthorityModule {}
