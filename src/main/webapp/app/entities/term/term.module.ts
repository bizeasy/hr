import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { TermComponent } from './term.component';
import { TermDetailComponent } from './term-detail.component';
import { TermUpdateComponent } from './term-update.component';
import { TermDeleteDialogComponent } from './term-delete-dialog.component';
import { termRoute } from './term.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(termRoute)],
  declarations: [TermComponent, TermDetailComponent, TermUpdateComponent, TermDeleteDialogComponent],
  entryComponents: [TermDeleteDialogComponent],
})
export class HrTermModule {}
