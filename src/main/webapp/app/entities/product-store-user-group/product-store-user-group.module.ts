import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ProductStoreUserGroupComponent } from './product-store-user-group.component';
import { ProductStoreUserGroupDetailComponent } from './product-store-user-group-detail.component';
import { ProductStoreUserGroupUpdateComponent } from './product-store-user-group-update.component';
import { ProductStoreUserGroupDeleteDialogComponent } from './product-store-user-group-delete-dialog.component';
import { productStoreUserGroupRoute } from './product-store-user-group.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(productStoreUserGroupRoute)],
  declarations: [
    ProductStoreUserGroupComponent,
    ProductStoreUserGroupDetailComponent,
    ProductStoreUserGroupUpdateComponent,
    ProductStoreUserGroupDeleteDialogComponent,
  ],
  entryComponents: [ProductStoreUserGroupDeleteDialogComponent],
})
export class HrProductStoreUserGroupModule {}
