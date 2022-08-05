import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICatalogueCategory, CatalogueCategory } from 'app/shared/model/catalogue-category.model';
import { CatalogueCategoryService } from './catalogue-category.service';
import { ICatalogue } from 'app/shared/model/catalogue.model';
import { CatalogueService } from 'app/entities/catalogue/catalogue.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category/product-category.service';
import { ICatalogueCategoryType } from 'app/shared/model/catalogue-category-type.model';
import { CatalogueCategoryTypeService } from 'app/entities/catalogue-category-type/catalogue-category-type.service';

type SelectableEntity = ICatalogue | IProductCategory | ICatalogueCategoryType;

@Component({
  selector: 'sys-catalogue-category-update',
  templateUrl: './catalogue-category-update.component.html',
})
export class CatalogueCategoryUpdateComponent implements OnInit {
  isSaving = false;
  catalogues: ICatalogue[] = [];
  productcategories: IProductCategory[] = [];
  cataloguecategorytypes: ICatalogueCategoryType[] = [];

  editForm = this.fb.group({
    id: [],
    sequenceNo: [],
    catalogue: [],
    productCategory: [],
    catalogueCategoryType: [],
  });

  constructor(
    protected catalogueCategoryService: CatalogueCategoryService,
    protected catalogueService: CatalogueService,
    protected productCategoryService: ProductCategoryService,
    protected catalogueCategoryTypeService: CatalogueCategoryTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ catalogueCategory }) => {
      this.updateForm(catalogueCategory);

      this.catalogueService.query().subscribe((res: HttpResponse<ICatalogue[]>) => (this.catalogues = res.body || []));

      this.productCategoryService.query().subscribe((res: HttpResponse<IProductCategory[]>) => (this.productcategories = res.body || []));

      this.catalogueCategoryTypeService
        .query()
        .subscribe((res: HttpResponse<ICatalogueCategoryType[]>) => (this.cataloguecategorytypes = res.body || []));
    });
  }

  updateForm(catalogueCategory: ICatalogueCategory): void {
    this.editForm.patchValue({
      id: catalogueCategory.id,
      sequenceNo: catalogueCategory.sequenceNo,
      catalogue: catalogueCategory.catalogue,
      productCategory: catalogueCategory.productCategory,
      catalogueCategoryType: catalogueCategory.catalogueCategoryType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const catalogueCategory = this.createFromForm();
    if (catalogueCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.catalogueCategoryService.update(catalogueCategory));
    } else {
      this.subscribeToSaveResponse(this.catalogueCategoryService.create(catalogueCategory));
    }
  }

  private createFromForm(): ICatalogueCategory {
    return {
      ...new CatalogueCategory(),
      id: this.editForm.get(['id'])!.value,
      sequenceNo: this.editForm.get(['sequenceNo'])!.value,
      catalogue: this.editForm.get(['catalogue'])!.value,
      productCategory: this.editForm.get(['productCategory'])!.value,
      catalogueCategoryType: this.editForm.get(['catalogueCategoryType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICatalogueCategory>>): void {
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
