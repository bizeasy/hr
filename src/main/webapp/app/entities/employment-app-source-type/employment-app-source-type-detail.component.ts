import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmploymentAppSourceType } from 'app/shared/model/employment-app-source-type.model';

@Component({
  selector: 'sys-employment-app-source-type-detail',
  templateUrl: './employment-app-source-type-detail.component.html',
})
export class EmploymentAppSourceTypeDetailComponent implements OnInit {
  employmentAppSourceType: IEmploymentAppSourceType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employmentAppSourceType }) => (this.employmentAppSourceType = employmentAppSourceType));
  }

  previousState(): void {
    window.history.back();
  }
}
