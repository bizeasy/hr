import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { LocationTypeComponent } from './location-type.component';
import { LocationTypeDetailComponent } from './location-type-detail.component';
import { LocationTypeUpdateComponent } from './location-type-update.component';
import { LocationTypeDeleteDialogComponent } from './location-type-delete-dialog.component';
import { locationTypeRoute } from './location-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(locationTypeRoute)],
  declarations: [LocationTypeComponent, LocationTypeDetailComponent, LocationTypeUpdateComponent, LocationTypeDeleteDialogComponent],
  entryComponents: [LocationTypeDeleteDialogComponent],
})
export class HrLocationTypeModule {}
