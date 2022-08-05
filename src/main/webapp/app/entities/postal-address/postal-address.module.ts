import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PostalAddressComponent } from './postal-address.component';
import { PostalAddressDetailComponent } from './postal-address-detail.component';
import { PostalAddressUpdateComponent } from './postal-address-update.component';
import { PostalAddressDeleteDialogComponent } from './postal-address-delete-dialog.component';
import { postalAddressRoute } from './postal-address.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(postalAddressRoute)],
  declarations: [PostalAddressComponent, PostalAddressDetailComponent, PostalAddressUpdateComponent, PostalAddressDeleteDialogComponent],
  entryComponents: [PostalAddressDeleteDialogComponent],
})
export class HrPostalAddressModule {}
