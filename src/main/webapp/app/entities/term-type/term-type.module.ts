import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { TermTypeComponent } from './term-type.component';
import { TermTypeDetailComponent } from './term-type-detail.component';
import { TermTypeUpdateComponent } from './term-type-update.component';
import { TermTypeDeleteDialogComponent } from './term-type-delete-dialog.component';
import { termTypeRoute } from './term-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(termTypeRoute)],
  declarations: [TermTypeComponent, TermTypeDetailComponent, TermTypeUpdateComponent, TermTypeDeleteDialogComponent],
  entryComponents: [TermTypeDeleteDialogComponent],
})
export class HrTermTypeModule {}
