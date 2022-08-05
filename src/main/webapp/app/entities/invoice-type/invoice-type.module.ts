import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { InvoiceTypeComponent } from './invoice-type.component';
import { InvoiceTypeDetailComponent } from './invoice-type-detail.component';
import { InvoiceTypeUpdateComponent } from './invoice-type-update.component';
import { InvoiceTypeDeleteDialogComponent } from './invoice-type-delete-dialog.component';
import { invoiceTypeRoute } from './invoice-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(invoiceTypeRoute)],
  declarations: [InvoiceTypeComponent, InvoiceTypeDetailComponent, InvoiceTypeUpdateComponent, InvoiceTypeDeleteDialogComponent],
  entryComponents: [InvoiceTypeDeleteDialogComponent],
})
export class HrInvoiceTypeModule {}
