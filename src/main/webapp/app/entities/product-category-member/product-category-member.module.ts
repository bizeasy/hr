import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ProductCategoryMemberComponent } from './product-category-member.component';
import { ProductCategoryMemberDetailComponent } from './product-category-member-detail.component';
import { ProductCategoryMemberUpdateComponent } from './product-category-member-update.component';
import { ProductCategoryMemberDeleteDialogComponent } from './product-category-member-delete-dialog.component';
import { productCategoryMemberRoute } from './product-category-member.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(productCategoryMemberRoute)],
  declarations: [
    ProductCategoryMemberComponent,
    ProductCategoryMemberDetailComponent,
    ProductCategoryMemberUpdateComponent,
    ProductCategoryMemberDeleteDialogComponent,
  ],
  entryComponents: [ProductCategoryMemberDeleteDialogComponent],
})
export class HrProductCategoryMemberModule {}
