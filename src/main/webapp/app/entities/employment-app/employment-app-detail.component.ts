import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmploymentApp } from 'app/shared/model/employment-app.model';

@Component({
  selector: 'sys-employment-app-detail',
  templateUrl: './employment-app-detail.component.html',
})
export class EmploymentAppDetailComponent implements OnInit {
  employmentApp: IEmploymentApp | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employmentApp }) => (this.employmentApp = employmentApp));
  }

  previousState(): void {
    window.history.back();
  }
}
