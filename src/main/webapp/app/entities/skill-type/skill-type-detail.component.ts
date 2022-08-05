import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISkillType } from 'app/shared/model/skill-type.model';

@Component({
  selector: 'sys-skill-type-detail',
  templateUrl: './skill-type-detail.component.html',
})
export class SkillTypeDetailComponent implements OnInit {
  skillType: ISkillType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ skillType }) => (this.skillType = skillType));
  }

  previousState(): void {
    window.history.back();
  }
}
