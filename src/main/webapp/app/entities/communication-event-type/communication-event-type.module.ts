import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { CommunicationEventTypeComponent } from './communication-event-type.component';
import { CommunicationEventTypeDetailComponent } from './communication-event-type-detail.component';
import { CommunicationEventTypeUpdateComponent } from './communication-event-type-update.component';
import { CommunicationEventTypeDeleteDialogComponent } from './communication-event-type-delete-dialog.component';
import { communicationEventTypeRoute } from './communication-event-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(communicationEventTypeRoute)],
  declarations: [
    CommunicationEventTypeComponent,
    CommunicationEventTypeDetailComponent,
    CommunicationEventTypeUpdateComponent,
    CommunicationEventTypeDeleteDialogComponent,
  ],
  entryComponents: [CommunicationEventTypeDeleteDialogComponent],
})
export class HrCommunicationEventTypeModule {}
