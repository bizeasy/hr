import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ProductStoreProductComponent } from './product-store-product.component';
import { ProductStoreProductDetailComponent } from './product-store-product-detail.component';
import { ProductStoreProductUpdateComponent } from './product-store-product-update.component';
import { ProductStoreProductDeleteDialogComponent } from './product-store-product-delete-dialog.component';
import { productStoreProductRoute } from './product-store-product.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(productStoreProductRoute)],
  declarations: [
    ProductStoreProductComponent,
    ProductStoreProductDetailComponent,
    ProductStoreProductUpdateComponent,
    ProductStoreProductDeleteDialogComponent,
  ],
  entryComponents: [ProductStoreProductDeleteDialogComponent],
})
export class HrProductStoreProductModule {}
