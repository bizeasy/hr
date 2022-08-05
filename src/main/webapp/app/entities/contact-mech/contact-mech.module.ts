import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ContactMechComponent } from './contact-mech.component';
import { ContactMechDetailComponent } from './contact-mech-detail.component';
import { ContactMechUpdateComponent } from './contact-mech-update.component';
import { ContactMechDeleteDialogComponent } from './contact-mech-delete-dialog.component';
import { contactMechRoute } from './contact-mech.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(contactMechRoute)],
  declarations: [ContactMechComponent, ContactMechDetailComponent, ContactMechUpdateComponent, ContactMechDeleteDialogComponent],
  entryComponents: [ContactMechDeleteDialogComponent],
})
export class HrContactMechModule {}
