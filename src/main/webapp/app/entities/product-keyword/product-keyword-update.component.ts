import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductKeyword, ProductKeyword } from 'app/shared/model/product-keyword.model';
import { ProductKeywordService } from './product-keyword.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';
import { IKeywordType } from 'app/shared/model/keyword-type.model';
import { KeywordTypeService } from 'app/entities/keyword-type/keyword-type.service';

type SelectableEntity = IProduct | IKeywordType;

@Component({
  selector: 'sys-product-keyword-update',
  templateUrl: './product-keyword-update.component.html',
})
export class ProductKeywordUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];
  keywordtypes: IKeywordType[] = [];

  editForm = this.fb.group({
    id: [],
    keyword: [null, [Validators.maxLength(60)]],
    relevancyWeight: [],
    product: [],
    keywordType: [],
  });

  constructor(
    protected productKeywordService: ProductKeywordService,
    protected productService: ProductService,
    protected keywordTypeService: KeywordTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productKeyword }) => {
      this.updateForm(productKeyword);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.keywordTypeService.query().subscribe((res: HttpResponse<IKeywordType[]>) => (this.keywordtypes = res.body || []));
    });
  }

  updateForm(productKeyword: IProductKeyword): void {
    this.editForm.patchValue({
      id: productKeyword.id,
      keyword: productKeyword.keyword,
      relevancyWeight: productKeyword.relevancyWeight,
      product: productKeyword.product,
      keywordType: productKeyword.keywordType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productKeyword = this.createFromForm();
    if (productKeyword.id !== undefined) {
      this.subscribeToSaveResponse(this.productKeywordService.update(productKeyword));
    } else {
      this.subscribeToSaveResponse(this.productKeywordService.create(productKeyword));
    }
  }

  private createFromForm(): IProductKeyword {
    return {
      ...new ProductKeyword(),
      id: this.editForm.get(['id'])!.value,
      keyword: this.editForm.get(['keyword'])!.value,
      relevancyWeight: this.editForm.get(['relevancyWeight'])!.value,
      product: this.editForm.get(['product'])!.value,
      keywordType: this.editForm.get(['keywordType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductKeyword>>): void {
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
