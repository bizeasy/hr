import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ProductKeywordComponent } from './product-keyword.component';
import { ProductKeywordDetailComponent } from './product-keyword-detail.component';
import { ProductKeywordUpdateComponent } from './product-keyword-update.component';
import { ProductKeywordDeleteDialogComponent } from './product-keyword-delete-dialog.component';
import { productKeywordRoute } from './product-keyword.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(productKeywordRoute)],
  declarations: [
    ProductKeywordComponent,
    ProductKeywordDetailComponent,
    ProductKeywordUpdateComponent,
    ProductKeywordDeleteDialogComponent,
  ],
  entryComponents: [ProductKeywordDeleteDialogComponent],
})
export class HrProductKeywordModule {}
