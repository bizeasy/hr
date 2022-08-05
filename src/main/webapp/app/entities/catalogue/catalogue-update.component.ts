import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatalogue, Catalogue } from 'app/shared/model/catalogue.model';
import { CatalogueService } from './catalogue.service';

@Component({
  selector: 'sys-catalogue-update',
  templateUrl: './catalogue-update.component.html',
})
export class CatalogueUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
    sequenceNo: [],
    imagePath: [],
    mobileImagePath: [],
    altImage1: [],
    altImage2: [],
    altImage3: [],
  });

  constructor(protected catalogueService: CatalogueService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catalogue }) => {
      this.updateForm(catalogue);
    });
  }

  updateForm(catalogue: ICatalogue): void {
    this.editForm.patchValue({
      id: catalogue.id,
      name: catalogue.name,
      description: catalogue.description,
      sequenceNo: catalogue.sequenceNo,
      imagePath: catalogue.imagePath,
      mobileImagePath: catalogue.mobileImagePath,
      altImage1: catalogue.altImage1,
      altImage2: catalogue.altImage2,
      altImage3: catalogue.altImage3,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catalogue = this.createFromForm();
    if (catalogue.id !== undefined) {
      this.subscribeToSaveResponse(this.catalogueService.update(catalogue));
    } else {
      this.subscribeToSaveResponse(this.catalogueService.create(catalogue));
    }
  }

  private createFromForm(): ICatalogue {
    return {
      ...new Catalogue(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      imagePath: this.editForm.get(['imagePath'])!.value,
      mobileImagePath: this.editForm.get(['mobileImagePath'])!.value,
      altImage1: this.editForm.get(['altImage1'])!.value,
      altImage2: this.editForm.get(['altImage2'])!.value,
      altImage3: this.editForm.get(['altImage3'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatalogue>>): void {
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
