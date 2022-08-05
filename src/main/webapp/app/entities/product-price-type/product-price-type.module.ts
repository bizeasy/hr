import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ProductPriceTypeComponent } from './product-price-type.component';
import { ProductPriceTypeDetailComponent } from './product-price-type-detail.component';
import { ProductPriceTypeUpdateComponent } from './product-price-type-update.component';
import { ProductPriceTypeDeleteDialogComponent } from './product-price-type-delete-dialog.component';
import { productPriceTypeRoute } from './product-price-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(productPriceTypeRoute)],
  declarations: [
    ProductPriceTypeComponent,
    ProductPriceTypeDetailComponent,
    ProductPriceTypeUpdateComponent,
    ProductPriceTypeDeleteDialogComponent,
  ],
  entryComponents: [ProductPriceTypeDeleteDialogComponent],
})
export class HrProductPriceTypeModule {}
