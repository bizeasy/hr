import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PartyClassificationGroupComponent } from './party-classification-group.component';
import { PartyClassificationGroupDetailComponent } from './party-classification-group-detail.component';
import { PartyClassificationGroupUpdateComponent } from './party-classification-group-update.component';
import { PartyClassificationGroupDeleteDialogComponent } from './party-classification-group-delete-dialog.component';
import { partyClassificationGroupRoute } from './party-classification-group.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(partyClassificationGroupRoute)],
  declarations: [
    PartyClassificationGroupComponent,
    PartyClassificationGroupDetailComponent,
    PartyClassificationGroupUpdateComponent,
    PartyClassificationGroupDeleteDialogComponent,
  ],
  entryComponents: [PartyClassificationGroupDeleteDialogComponent],
})
export class HrPartyClassificationGroupModule {}
