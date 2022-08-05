import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProductCategory, ProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from './product-category.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IProductCategoryType } from 'app/shared/model/product-category-type.model';
import { ProductCategoryTypeService } from 'app/entities/product-category-type/product-category-type.service';

type SelectableEntity = IProductCategoryType | IProductCategory;

@Component({
  selector: 'sys-product-category-update',
  templateUrl: './product-category-update.component.html',
})
export class ProductCategoryUpdateComponent implements OnInit {
  isSaving = false;
  productcategorytypes: IProductCategoryType[] = [];
  productcategories: IProductCategory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(60)]],
    description: [null, [Validators.maxLength(100)]],
    longDescription: [],
    attribute: [null, [Validators.maxLength(25)]],
    sequenceNo: [],
    imageUrl: [],
    altImageUrl: [],
    info: [],
    productCategoryType: [],
    parent: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected productCategoryService: ProductCategoryService,
    protected productCategoryTypeService: ProductCategoryTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productCategory }) => {
      this.updateForm(productCategory);

      this.productCategoryTypeService
        .query()
        .subscribe((res: HttpResponse<IProductCategoryType[]>) => (this.productcategorytypes = res.body || []));

      this.productCategoryService.query().subscribe((res: HttpResponse<IProductCategory[]>) => (this.productcategories = res.body || []));
    });
  }

  updateForm(productCategory: IProductCategory): void {
    this.editForm.patchValue({
      id: productCategory.id,
      name: productCategory.name,
      description: productCategory.description,
      longDescription: productCategory.longDescription,
      attribute: productCategory.attribute,
      sequenceNo: productCategory.sequenceNo,
      imageUrl: productCategory.imageUrl,
      altImageUrl: productCategory.altImageUrl,
      info: productCategory.info,
      productCategoryType: productCategory.productCategoryType,
      parent: productCategory.parent,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('hrApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productCategory = this.createFromForm();
    if (productCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.productCategoryService.update(productCategory));
    } else {
      this.subscribeToSaveResponse(this.productCategoryService.create(productCategory));
    }
  }

  private createFromForm(): IProductCategory {
    return {
      ...new ProductCategory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      longDescription: this.editForm.get(['longDescription'])!.value,
      attribute: this.editForm.get(['attribute'])!.value,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      imageUrl: this.editForm.get(['imageUrl'])!.value,
      altImageUrl: this.editForm.get(['altImageUrl'])!.value,
      info: this.editForm.get(['info'])!.value,
      productCategoryType: this.editForm.get(['productCategoryType'])!.value,
      parent: this.editForm.get(['parent'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductCategory>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
