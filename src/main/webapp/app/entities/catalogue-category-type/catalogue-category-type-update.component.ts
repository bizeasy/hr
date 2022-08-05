import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatalogueCategoryType, CatalogueCategoryType } from 'app/shared/model/catalogue-category-type.model';
import { CatalogueCategoryTypeService } from './catalogue-category-type.service';

@Component({
  selector: 'sys-catalogue-category-type-update',
  templateUrl: './catalogue-category-type-update.component.html',
})
export class CatalogueCategoryTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
  });

  constructor(
    protected catalogueCategoryTypeService: CatalogueCategoryTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catalogueCategoryType }) => {
      this.updateForm(catalogueCategoryType);
    });
  }

  updateForm(catalogueCategoryType: ICatalogueCategoryType): void {
    this.editForm.patchValue({
      id: catalogueCategoryType.id,
      name: catalogueCategoryType.name,
      description: catalogueCategoryType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catalogueCategoryType = this.createFromForm();
    if (catalogueCategoryType.id !== undefined) {
      this.subscribeToSaveResponse(this.catalogueCategoryTypeService.update(catalogueCategoryType));
    } else {
      this.subscribeToSaveResponse(this.catalogueCategoryTypeService.create(catalogueCategoryType));
    }
  }

  private createFromForm(): ICatalogueCategoryType {
    return {
      ...new CatalogueCategoryType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatalogueCategoryType>>): void {
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
