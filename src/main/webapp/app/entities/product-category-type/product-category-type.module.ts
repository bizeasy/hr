import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ProductCategoryTypeComponent } from './product-category-type.component';
import { ProductCategoryTypeDetailComponent } from './product-category-type-detail.component';
import { ProductCategoryTypeUpdateComponent } from './product-category-type-update.component';
import { ProductCategoryTypeDeleteDialogComponent } from './product-category-type-delete-dialog.component';
import { productCategoryTypeRoute } from './product-category-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(productCategoryTypeRoute)],
  declarations: [
    ProductCategoryTypeComponent,
    ProductCategoryTypeDetailComponent,
    ProductCategoryTypeUpdateComponent,
    ProductCategoryTypeDeleteDialogComponent,
  ],
  entryComponents: [ProductCategoryTypeDeleteDialogComponent],
})
export class HrProductCategoryTypeModule {}
