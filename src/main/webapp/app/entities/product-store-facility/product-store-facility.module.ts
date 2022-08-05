import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ProductStoreFacilityComponent } from './product-store-facility.component';
import { ProductStoreFacilityDetailComponent } from './product-store-facility-detail.component';
import { ProductStoreFacilityUpdateComponent } from './product-store-facility-update.component';
import { ProductStoreFacilityDeleteDialogComponent } from './product-store-facility-delete-dialog.component';
import { productStoreFacilityRoute } from './product-store-facility.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(productStoreFacilityRoute)],
  declarations: [
    ProductStoreFacilityComponent,
    ProductStoreFacilityDetailComponent,
    ProductStoreFacilityUpdateComponent,
    ProductStoreFacilityDeleteDialogComponent,
  ],
  entryComponents: [ProductStoreFacilityDeleteDialogComponent],
})
export class HrProductStoreFacilityModule {}
