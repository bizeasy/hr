import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILot, Lot } from 'app/shared/model/lot.model';
import { LotService } from './lot.service';

@Component({
  selector: 'sys-lot-update',
  templateUrl: './lot-update.component.html',
})
export class LotUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    creationDate: [],
    quantity: [],
    expirationDate: [],
    retestDate: [],
  });

  constructor(protected lotService: LotService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lot }) => {
      if (!lot.id) {
        const today = moment().startOf('day');
        lot.creationDate = today;
        lot.expirationDate = today;
        lot.retestDate = today;
      }

      this.updateForm(lot);
    });
  }

  updateForm(lot: ILot): void {
    this.editForm.patchValue({
      id: lot.id,
      creationDate: lot.creationDate ? lot.creationDate.format(DATE_TIME_FORMAT) : null,
      quantity: lot.quantity,
      expirationDate: lot.expirationDate ? lot.expirationDate.format(DATE_TIME_FORMAT) : null,
      retestDate: lot.retestDate ? lot.retestDate.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lot = this.createFromForm();
    if (lot.id !== undefined) {
      this.subscribeToSaveResponse(this.lotService.update(lot));
    } else {
      this.subscribeToSaveResponse(this.lotService.create(lot));
    }
  }

  private createFromForm(): ILot {
    return {
      ...new Lot(),
      id: this.editForm.get(['id'])!.value,
      creationDate: this.editForm.get(['creationDate'])!.value
        ? moment(this.editForm.get(['creationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      quantity: this.editForm.get(['quantity'])!.value,
      expirationDate: this.editForm.get(['expirationDate'])!.value
        ? moment(this.editForm.get(['expirationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      retestDate: this.editForm.get(['retestDate'])!.value ? moment(this.editForm.get(['retestDate'])!.value, DATE_TIME_FORMAT) : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILot>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
