import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IKeywordType, KeywordType } from 'app/shared/model/keyword-type.model';
import { KeywordTypeService } from './keyword-type.service';

@Component({
  selector: 'sys-keyword-type-update',
  templateUrl: './keyword-type-update.component.html',
})
export class KeywordTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
  });

  constructor(protected keywordTypeService: KeywordTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ keywordType }) => {
      this.updateForm(keywordType);
    });
  }

  updateForm(keywordType: IKeywordType): void {
    this.editForm.patchValue({
      id: keywordType.id,
      name: keywordType.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const keywordType = this.createFromForm();
    if (keywordType.id !== undefined) {
      this.subscribeToSaveResponse(this.keywordTypeService.update(keywordType));
    } else {
      this.subscribeToSaveResponse(this.keywordTypeService.create(keywordType));
    }
  }

  private createFromForm(): IKeywordType {
    return {
      ...new KeywordType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKeywordType>>): void {
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
