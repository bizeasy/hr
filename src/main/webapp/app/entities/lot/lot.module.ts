import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { LotComponent } from './lot.component';
import { LotDetailComponent } from './lot-detail.component';
import { LotUpdateComponent } from './lot-update.component';
import { LotDeleteDialogComponent } from './lot-delete-dialog.component';
import { lotRoute } from './lot.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(lotRoute)],
  declarations: [LotComponent, LotDetailComponent, LotUpdateComponent, LotDeleteDialogComponent],
  entryComponents: [LotDeleteDialogComponent],
})
export class HrLotModule {}
