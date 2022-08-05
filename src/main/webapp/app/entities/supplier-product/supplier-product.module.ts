import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { SupplierProductComponent } from './supplier-product.component';
import { SupplierProductDetailComponent } from './supplier-product-detail.component';
import { SupplierProductUpdateComponent } from './supplier-product-update.component';
import { SupplierProductDeleteDialogComponent } from './supplier-product-delete-dialog.component';
import { supplierProductRoute } from './supplier-product.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(supplierProductRoute)],
  declarations: [
    SupplierProductComponent,
    SupplierProductDetailComponent,
    SupplierProductUpdateComponent,
    SupplierProductDeleteDialogComponent,
  ],
  entryComponents: [SupplierProductDeleteDialogComponent],
})
export class HrSupplierProductModule {}
