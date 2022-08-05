import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrSharedModule } from 'app/shared/shared.module';
import { SkillTypeComponent } from './skill-type.component';
import { SkillTypeDetailComponent } from './skill-type-detail.component';
import { SkillTypeUpdateComponent } from './skill-type-update.component';
import { SkillTypeDeleteDialogComponent } from './skill-type-delete-dialog.component';
import { skillTypeRoute } from './skill-type.route';

@NgModule({
  imports: [HrSharedModule, RouterModule.forChild(skillTypeRoute)],
  declarations: [SkillTypeComponent, SkillTypeDetailComponent, SkillTypeUpdateComponent, SkillTypeDeleteDialogComponent],
  entryComponents: [SkillTypeDeleteDialogComponent],
})
export class HrSkillTypeModule {}
