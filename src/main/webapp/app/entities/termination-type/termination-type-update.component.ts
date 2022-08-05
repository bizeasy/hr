import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITerminationType, TerminationType } from 'app/shared/model/termination-type.model';
import { TerminationTypeService } from './termination-type.service';

@Component({
  selector: 'sys-termination-type-update',
  templateUrl: './termination-type-update.component.html',
})
export class TerminationTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(20)]],
    description: [null, [Validators.maxLength(40)]],
  });

  constructor(
    protected terminationTypeService: TerminationTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ terminationType }) => {
      this.updateForm(terminationType);
    });
  }

  updateForm(terminationType: ITerminationType): void {
    this.editForm.patchValue({
      id: terminationType.id,
      name: terminationType.name,
      description: terminationType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const terminationType = this.createFromForm();
    if (terminationType.id !== undefined) {
      this.subscribeToSaveResponse(this.terminationTypeService.update(terminationType));
    } else {
      this.subscribeToSaveResponse(this.terminationTypeService.create(terminationType));
    }
  }

  private createFromForm(): ITerminationType {
    return {
      ...new TerminationType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITerminationType>>): void {
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
