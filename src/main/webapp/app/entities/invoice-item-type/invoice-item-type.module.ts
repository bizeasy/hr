import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { InvoiceItemTypeComponent } from './invoice-item-type.component';
import { InvoiceItemTypeDetailComponent } from './invoice-item-type-detail.component';
import { InvoiceItemTypeUpdateComponent } from './invoice-item-type-update.component';
import { InvoiceItemTypeDeleteDialogComponent } from './invoice-item-type-delete-dialog.component';
import { invoiceItemTypeRoute } from './invoice-item-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(invoiceItemTypeRoute)],
  declarations: [
    InvoiceItemTypeComponent,
    InvoiceItemTypeDetailComponent,
    InvoiceItemTypeUpdateComponent,
    InvoiceItemTypeDeleteDialogComponent,
  ],
  entryComponents: [InvoiceItemTypeDeleteDialogComponent],
})
export class HrInvoiceItemTypeModule {}
