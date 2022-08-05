import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUomType } from 'app/shared/model/uom-type.model';

@Component({
  selector: 'sys-uom-type-detail',
  templateUrl: './uom-type-detail.component.html',
})
export class UomTypeDetailComponent implements OnInit {
  uomType: IUomType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uomType }) => (this.uomType = uomType));
  }

  previousState(): void {
    window.history.back();
  }
}
