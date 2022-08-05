import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ProductPricePurposeComponent } from './product-price-purpose.component';
import { ProductPricePurposeDetailComponent } from './product-price-purpose-detail.component';
import { ProductPricePurposeUpdateComponent } from './product-price-purpose-update.component';
import { ProductPricePurposeDeleteDialogComponent } from './product-price-purpose-delete-dialog.component';
import { productPricePurposeRoute } from './product-price-purpose.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(productPricePurposeRoute)],
  declarations: [
    ProductPricePurposeComponent,
    ProductPricePurposeDetailComponent,
    ProductPricePurposeUpdateComponent,
    ProductPricePurposeDeleteDialogComponent,
  ],
  entryComponents: [ProductPricePurposeDeleteDialogComponent],
})
export class HrProductPricePurposeModule {}
