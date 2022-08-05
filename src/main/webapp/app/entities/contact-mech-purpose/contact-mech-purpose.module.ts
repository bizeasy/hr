import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ContactMechPurposeComponent } from './contact-mech-purpose.component';
import { ContactMechPurposeDetailComponent } from './contact-mech-purpose-detail.component';
import { ContactMechPurposeUpdateComponent } from './contact-mech-purpose-update.component';
import { ContactMechPurposeDeleteDialogComponent } from './contact-mech-purpose-delete-dialog.component';
import { contactMechPurposeRoute } from './contact-mech-purpose.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(contactMechPurposeRoute)],
  declarations: [
    ContactMechPurposeComponent,
    ContactMechPurposeDetailComponent,
    ContactMechPurposeUpdateComponent,
    ContactMechPurposeDeleteDialogComponent,
  ],
  entryComponents: [ContactMechPurposeDeleteDialogComponent],
})
export class HrContactMechPurposeModule {}
