import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { GeoAssocTypeComponent } from './geo-assoc-type.component';
import { GeoAssocTypeDetailComponent } from './geo-assoc-type-detail.component';
import { GeoAssocTypeUpdateComponent } from './geo-assoc-type-update.component';
import { GeoAssocTypeDeleteDialogComponent } from './geo-assoc-type-delete-dialog.component';
import { geoAssocTypeRoute } from './geo-assoc-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(geoAssocTypeRoute)],
  declarations: [GeoAssocTypeComponent, GeoAssocTypeDetailComponent, GeoAssocTypeUpdateComponent, GeoAssocTypeDeleteDialogComponent],
  entryComponents: [GeoAssocTypeDeleteDialogComponent],
})
export class HrGeoAssocTypeModule {}
