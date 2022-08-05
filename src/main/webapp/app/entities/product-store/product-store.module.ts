import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ProductStoreComponent } from './product-store.component';
import { ProductStoreDetailComponent } from './product-store-detail.component';
import { ProductStoreUpdateComponent } from './product-store-update.component';
import { ProductStoreDeleteDialogComponent } from './product-store-delete-dialog.component';
import { productStoreRoute } from './product-store.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(productStoreRoute)],
  declarations: [ProductStoreComponent, ProductStoreDetailComponent, ProductStoreUpdateComponent, ProductStoreDeleteDialogComponent],
  entryComponents: [ProductStoreDeleteDialogComponent],
})
export class HrProductStoreModule {}
