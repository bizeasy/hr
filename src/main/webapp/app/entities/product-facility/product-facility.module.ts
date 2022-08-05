import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ProductFacilityComponent } from './product-facility.component';
import { ProductFacilityDetailComponent } from './product-facility-detail.component';
import { ProductFacilityUpdateComponent } from './product-facility-update.component';
import { ProductFacilityDeleteDialogComponent } from './product-facility-delete-dialog.component';
import { productFacilityRoute } from './product-facility.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(productFacilityRoute)],
  declarations: [
    ProductFacilityComponent,
    ProductFacilityDetailComponent,
    ProductFacilityUpdateComponent,
    ProductFacilityDeleteDialogComponent,
  ],
  entryComponents: [ProductFacilityDeleteDialogComponent],
})
export class HrProductFacilityModule {}
