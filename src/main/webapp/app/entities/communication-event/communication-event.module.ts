import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { CommunicationEventComponent } from './communication-event.component';
import { CommunicationEventDetailComponent } from './communication-event-detail.component';
import { CommunicationEventUpdateComponent } from './communication-event-update.component';
import { CommunicationEventDeleteDialogComponent } from './communication-event-delete-dialog.component';
import { communicationEventRoute } from './communication-event.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(communicationEventRoute)],
  declarations: [
    CommunicationEventComponent,
    CommunicationEventDetailComponent,
    CommunicationEventUpdateComponent,
    CommunicationEventDeleteDialogComponent,
  ],
  entryComponents: [CommunicationEventDeleteDialogComponent],
})
export class HrCommunicationEventModule {}
