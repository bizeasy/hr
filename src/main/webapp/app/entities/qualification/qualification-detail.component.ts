import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQualification } from 'app/shared/model/qualification.model';

@Component({
  selector: 'sys-qualification-detail',
  templateUrl: './qualification-detail.component.html',
})
export class QualificationDetailComponent implements OnInit {
  qualification: IQualification | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ qualification }) => (this.qualification = qualification));
  }

  previousState(): void {
    window.history.back();
  }
}
