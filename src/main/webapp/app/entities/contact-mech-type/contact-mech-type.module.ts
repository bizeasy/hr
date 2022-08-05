import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { ContactMechTypeComponent } from './contact-mech-type.component';
import { ContactMechTypeDetailComponent } from './contact-mech-type-detail.component';
import { ContactMechTypeUpdateComponent } from './contact-mech-type-update.component';
import { ContactMechTypeDeleteDialogComponent } from './contact-mech-type-delete-dialog.component';
import { contactMechTypeRoute } from './contact-mech-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(contactMechTypeRoute)],
  declarations: [
    ContactMechTypeComponent,
    ContactMechTypeDetailComponent,
    ContactMechTypeUpdateComponent,
    ContactMechTypeDeleteDialogComponent,
  ],
  entryComponents: [ContactMechTypeDeleteDialogComponent],
})
export class HrContactMechTypeModule {}
