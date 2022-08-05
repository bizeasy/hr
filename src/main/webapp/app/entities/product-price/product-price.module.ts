import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ProductPriceComponent } from './product-price.component';
import { ProductPriceDetailComponent } from './product-price-detail.component';
import { ProductPriceUpdateComponent } from './product-price-update.component';
import { ProductPriceDeleteDialogComponent } from './product-price-delete-dialog.component';
import { productPriceRoute } from './product-price.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(productPriceRoute)],
  declarations: [ProductPriceComponent, ProductPriceDetailComponent, ProductPriceUpdateComponent, ProductPriceDeleteDialogComponent],
  entryComponents: [ProductPriceDeleteDialogComponent],
})
export class HrProductPriceModule {}
