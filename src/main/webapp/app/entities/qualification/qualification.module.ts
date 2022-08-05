import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { QualificationComponent } from './qualification.component';
import { QualificationDetailComponent } from './qualification-detail.component';
import { QualificationUpdateComponent } from './qualification-update.component';
import { QualificationDeleteDialogComponent } from './qualification-delete-dialog.component';
import { qualificationRoute } from './qualification.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(qualificationRoute)],
  declarations: [QualificationComponent, QualificationDetailComponent, QualificationUpdateComponent, QualificationDeleteDialogComponent],
  entryComponents: [QualificationDeleteDialogComponent],
})
export class HrQualificationModule {}
