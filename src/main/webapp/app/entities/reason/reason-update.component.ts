import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IReason, Reason } from 'app/shared/model/reason.model';
import { ReasonService } from './reason.service';
import { IReasonType } from 'app/shared/model/reason-type.model';
import { ReasonTypeService } from 'app/entities/reason-type/reason-type.service';

@Component({
  selector: 'sys-reason-update',
  templateUrl: './reason-update.component.html',
})
export class ReasonUpdateComponent implements OnInit {
  isSaving = false;
  reasontypes: IReasonType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
    description: [null, [Validators.maxLength(255)]],
    reasonType: [],
  });

  constructor(
    protected reasonService: ReasonService,
    protected reasonTypeService: ReasonTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reason }) => {
      this.updateForm(reason);

      this.reasonTypeService.query().subscribe((res: HttpResponse<IReasonType[]>) => (this.reasontypes = res.body || []));
    });
  }

  updateForm(reason: IReason): void {
    this.editForm.patchValue({
      id: reason.id,
      name: reason.name,
      description: reason.description,
      reasonType: reason.reasonType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reason = this.createFromForm();
    if (reason.id !== undefined) {
      this.subscribeToSaveResponse(this.reasonService.update(reason));
    } else {
      this.subscribeToSaveResponse(this.reasonService.create(reason));
    }
  }

  private createFromForm(): IReason {
    return {
      ...new Reason(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      reasonType: this.editForm.get(['reasonType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReason>>): void {
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

  trackById(index: number, item: IReasonType): any {
    return item.id;
  }
}
