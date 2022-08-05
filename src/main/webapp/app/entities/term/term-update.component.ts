import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITerm, Term } from 'app/shared/model/term.model';
import { TermService } from './term.service';
import { ITermType } from 'app/shared/model/term-type.model';
import { TermTypeService } from 'app/entities/term-type/term-type.service';

@Component({
  selector: 'sys-term-update',
  templateUrl: './term-update.component.html',
})
export class TermUpdateComponent implements OnInit {
  isSaving = false;
  termtypes: ITermType[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
    termDetail: [],
    termValue: [],
    termDays: [],
    textValue: [],
    termType: [],
  });

  constructor(
    protected termService: TermService,
    protected termTypeService: TermTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ term }) => {
      this.updateForm(term);

      this.termTypeService.query().subscribe((res: HttpResponse<ITermType[]>) => (this.termtypes = res.body || []));
    });
  }

  updateForm(term: ITerm): void {
    this.editForm.patchValue({
      id: term.id,
      name: term.name,
      description: term.description,
      termDetail: term.termDetail,
      termValue: term.termValue,
      termDays: term.termDays,
      textValue: term.textValue,
      termType: term.termType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const term = this.createFromForm();
    if (term.id !== undefined) {
      this.subscribeToSaveResponse(this.termService.update(term));
    } else {
      this.subscribeToSaveResponse(this.termService.create(term));
    }
  }

  private createFromForm(): ITerm {
    return {
      ...new Term(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      termDetail: this.editForm.get(['termDetail'])!.value,
      termValue: this.editForm.get(['termValue'])!.value,
      termDays: this.editForm.get(['termDays'])!.value,
      textValue: this.editForm.get(['textValue'])!.value,
      termType: this.editForm.get(['termType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITerm>>): void {
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

  trackById(index: number, item: ITermType): any {
    return item.id;
  }
}
