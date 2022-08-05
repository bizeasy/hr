import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { PartyResumeComponent } from './party-resume.component';
import { PartyResumeDetailComponent } from './party-resume-detail.component';
import { PartyResumeUpdateComponent } from './party-resume-update.component';
import { PartyResumeDeleteDialogComponent } from './party-resume-delete-dialog.component';
import { partyResumeRoute } from './party-resume.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(partyResumeRoute)],
  declarations: [PartyResumeComponent, PartyResumeDetailComponent, PartyResumeUpdateComponent, PartyResumeDeleteDialogComponent],
  entryComponents: [PartyResumeDeleteDialogComponent],
})
export class HrPartyResumeModule {}
