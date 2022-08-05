import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ProductStoreTypeComponent } from './product-store-type.component';
import { ProductStoreTypeDetailComponent } from './product-store-type-detail.component';
import { ProductStoreTypeUpdateComponent } from './product-store-type-update.component';
import { ProductStoreTypeDeleteDialogComponent } from './product-store-type-delete-dialog.component';
import { productStoreTypeRoute } from './product-store-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(productStoreTypeRoute)],
  declarations: [
    ProductStoreTypeComponent,
    ProductStoreTypeDetailComponent,
    ProductStoreTypeUpdateComponent,
    ProductStoreTypeDeleteDialogComponent,
  ],
  entryComponents: [ProductStoreTypeDeleteDialogComponent],
})
export class HrProductStoreTypeModule {}
