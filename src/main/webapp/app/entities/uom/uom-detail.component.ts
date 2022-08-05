import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUom } from 'app/shared/model/uom.model';

@Component({
  selector: 'sys-uom-detail',
  templateUrl: './uom-detail.component.html',
})
export class UomDetailComponent implements OnInit {
  uom: IUom | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uom }) => (this.uom = uom));
  }

  previousState(): void {
    window.history.back();
  }
}
