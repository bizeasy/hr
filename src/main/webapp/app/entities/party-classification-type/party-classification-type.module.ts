import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PartyClassificationTypeComponent } from './party-classification-type.component';
import { PartyClassificationTypeDetailComponent } from './party-classification-type-detail.component';
import { PartyClassificationTypeUpdateComponent } from './party-classification-type-update.component';
import { PartyClassificationTypeDeleteDialogComponent } from './party-classification-type-delete-dialog.component';
import { partyClassificationTypeRoute } from './party-classification-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(partyClassificationTypeRoute)],
  declarations: [
    PartyClassificationTypeComponent,
    PartyClassificationTypeDetailComponent,
    PartyClassificationTypeUpdateComponent,
    PartyClassificationTypeDeleteDialogComponent,
  ],
  entryComponents: [PartyClassificationTypeDeleteDialogComponent],
})
export class HrPartyClassificationTypeModule {}
