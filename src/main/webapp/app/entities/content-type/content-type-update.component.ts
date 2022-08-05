import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContentType, ContentType } from 'app/shared/model/content-type.model';
import { ContentTypeService } from './content-type.service';

@Component({
  selector: 'sys-content-type-update',
  templateUrl: './content-type-update.component.html',
})
export class ContentTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(25)]],
  });

  constructor(protected contentTypeService: ContentTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentType }) => {
      this.updateForm(contentType);
    });
  }

  updateForm(contentType: IContentType): void {
    this.editForm.patchValue({
      id: contentType.id,
      name: contentType.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contentType = this.createFromForm();
    if (contentType.id !== undefined) {
      this.subscribeToSaveResponse(this.contentTypeService.update(contentType));
    } else {
      this.subscribeToSaveResponse(this.contentTypeService.create(contentType));
    }
  }

  private createFromForm(): IContentType {
    return {
      ...new ContentType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContentType>>): void {
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
