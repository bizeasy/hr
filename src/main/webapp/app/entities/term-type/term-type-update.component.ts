import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITermType, TermType } from 'app/shared/model/term-type.model';
import { TermTypeService } from './term-type.service';

@Component({
  selector: 'sys-term-type-update',
  templateUrl: './term-type-update.component.html',
})
export class TermTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(protected termTypeService: TermTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ termType }) => {
      this.updateForm(termType);
    });
  }

  updateForm(termType: ITermType): void {
    this.editForm.patchValue({
      id: termType.id,
      name: termType.name,
      description: termType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const termType = this.createFromForm();
    if (termType.id !== undefined) {
      this.subscribeToSaveResponse(this.termTypeService.update(termType));
    } else {
      this.subscribeToSaveResponse(this.termTypeService.create(termType));
    }
  }

  private createFromForm(): ITermType {
    return {
      ...new TermType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITermType>>): void {
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
