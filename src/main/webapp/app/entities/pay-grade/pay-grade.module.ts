import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PayGradeComponent } from './pay-grade.component';
import { PayGradeDetailComponent } from './pay-grade-detail.component';
import { PayGradeUpdateComponent } from './pay-grade-update.component';
import { PayGradeDeleteDialogComponent } from './pay-grade-delete-dialog.component';
import { payGradeRoute } from './pay-grade.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(payGradeRoute)],
  declarations: [PayGradeComponent, PayGradeDetailComponent, PayGradeUpdateComponent, PayGradeDeleteDialogComponent],
  entryComponents: [PayGradeDeleteDialogComponent],
})
export class HrPayGradeModule {}
