import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductService } from './product.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IProductType } from 'app/shared/model/product-type.model';
import { ProductTypeService } from 'app/entities/product-type/product-type.service';
import { IUom } from 'app/shared/model/uom.model';
import { UomService } from 'app/entities/uom/uom.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category/product-category.service';
import { IInventoryItemType } from 'app/shared/model/inventory-item-type.model';
import { InventoryItemTypeService } from 'app/entities/inventory-item-type/inventory-item-type.service';
import { ITaxSlab } from 'app/shared/model/tax-slab.model';
import { TaxSlabService } from 'app/entities/tax-slab/tax-slab.service';

type SelectableEntity = IProductType | IUom | IProductCategory | IProduct | IInventoryItemType | ITaxSlab;

@Component({
  selector: 'sys-product-update',
  templateUrl: './product-update.component.html',
})
export class ProductUpdateComponent implements OnInit {
  isSaving = false;
  producttypes: IProductType[] = [];
  uoms: IUom[] = [];
  productcategories: IProductCategory[] = [];
  products: IProduct[] = [];
  inventoryitemtypes: IInventoryItemType[] = [];
  taxslabs: ITaxSlab[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.maxLength(100)]],
    internalName: [null, [Validators.maxLength(60)]],
    brandName: [null, [Validators.maxLength(60)]],
    variant: [null, [Validators.maxLength(60)]],
    sku: [null, [Validators.maxLength(60)]],
    introductionDate: [],
    releaseDate: [],
    quantityIncluded: [],
    piecesIncluded: [],
    weight: [],
    height: [],
    width: [],
    depth: [],
    smallImageUrl: [null, [Validators.maxLength(2000)]],
    mediumImageUrl: [null, [Validators.maxLength(2000)]],
    largeImageUrl: [null, [Validators.maxLength(2000)]],
    detailImageUrl: [null, [Validators.maxLength(2000)]],
    originalImageUrl: [null, [Validators.maxLength(2000)]],
    quantity: [],
    cgst: [],
    igst: [],
    sgst: [],
    price: [],
    cgstPercentage: [],
    igstPercentage: [],
    sgstPercentage: [],
    totalPrice: [],
    description: [null, [Validators.maxLength(100)]],
    longDescription: [],
    info: [],
    isVirtual: [],
    isVariant: [],
    requireInventory: [],
    returnable: [],
    isPopular: [],
    changeControlNo: [null, [Validators.maxLength(20)]],
    retestDuration: [],
    expiryDuration: [],
    validationType: [null, [Validators.maxLength(20)]],
    shelfLife: [],
    productType: [],
    quantityUom: [],
    weightUom: [],
    sizeUom: [],
    uom: [],
    primaryProductCategory: [],
    virtualProduct: [],
    inventoryItemType: [],
    taxSlab: [],
    shelfLifeUom: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected productService: ProductService,
    protected productTypeService: ProductTypeService,
    protected uomService: UomService,
    protected productCategoryService: ProductCategoryService,
    protected inventoryItemTypeService: InventoryItemTypeService,
    protected taxSlabService: TaxSlabService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ product }) => {
      if (!product.id) {
        const today = moment().startOf('day');
        product.introductionDate = today;
        product.releaseDate = today;
      }

      this.updateForm(product);

      this.productTypeService.query().subscribe((res: HttpResponse<IProductType[]>) => (this.producttypes = res.body || []));

      this.uomService.query().subscribe((res: HttpResponse<IUom[]>) => (this.uoms = res.body || []));

      this.productCategoryService.query().subscribe((res: HttpResponse<IProductCategory[]>) => (this.productcategories = res.body || []));

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.inventoryItemTypeService
        .query()
        .subscribe((res: HttpResponse<IInventoryItemType[]>) => (this.inventoryitemtypes = res.body || []));

      this.taxSlabService.query().subscribe((res: HttpResponse<ITaxSlab[]>) => (this.taxslabs = res.body || []));
    });
  }

  updateForm(product: IProduct): void {
    this.editForm.patchValue({
      id: product.id,
      name: product.name,
      internalName: product.internalName,
      brandName: product.brandName,
      variant: product.variant,
      sku: product.sku,
      introductionDate: product.introductionDate ? product.introductionDate.format(DATE_TIME_FORMAT) : null,
      releaseDate: product.releaseDate ? product.releaseDate.format(DATE_TIME_FORMAT) : null,
      quantityIncluded: product.quantityIncluded,
      piecesIncluded: product.piecesIncluded,
      weight: product.weight,
      height: product.height,
      width: product.width,
      depth: product.depth,
      smallImageUrl: product.smallImageUrl,
      mediumImageUrl: product.mediumImageUrl,
      largeImageUrl: product.largeImageUrl,
      detailImageUrl: product.detailImageUrl,
      originalImageUrl: product.originalImageUrl,
      quantity: product.quantity,
      cgst: product.cgst,
      igst: product.igst,
      sgst: product.sgst,
      price: product.price,
      cgstPercentage: product.cgstPercentage,
      igstPercentage: product.igstPercentage,
      sgstPercentage: product.sgstPercentage,
      totalPrice: product.totalPrice,
      description: product.description,
      longDescription: product.longDescription,
      info: product.info,
      isVirtual: product.isVirtual,
      isVariant: product.isVariant,
      requireInventory: product.requireInventory,
      returnable: product.returnable,
      isPopular: product.isPopular,
      changeControlNo: product.changeControlNo,
      retestDuration: product.retestDuration,
      expiryDuration: product.expiryDuration,
      validationType: product.validationType,
      shelfLife: product.shelfLife,
      productType: product.productType,
      quantityUom: product.quantityUom,
      weightUom: product.weightUom,
      sizeUom: product.sizeUom,
      uom: product.uom,
      primaryProductCategory: product.primaryProductCategory,
      virtualProduct: product.virtualProduct,
      inventoryItemType: product.inventoryItemType,
      taxSlab: product.taxSlab,
      shelfLifeUom: product.shelfLifeUom,
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
    const product = this.createFromForm();
    if (product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(product));
    }
  }

  private createFromForm(): IProduct {
    return {
      ...new Product(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      internalName: this.editForm.get(['internalName'])!.value,
      brandName: this.editForm.get(['brandName'])!.value,
      variant: this.editForm.get(['variant'])!.value,
      sku: this.editForm.get(['sku'])!.value,
      introductionDate: this.editForm.get(['introductionDate'])!.value
        ? moment(this.editForm.get(['introductionDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      releaseDate: this.editForm.get(['releaseDate'])!.value
        ? moment(this.editForm.get(['releaseDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      quantityIncluded: this.editForm.get(['quantityIncluded'])!.value,
      piecesIncluded: this.editForm.get(['piecesIncluded'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      height: this.editForm.get(['height'])!.value,
      width: this.editForm.get(['width'])!.value,
      depth: this.editForm.get(['depth'])!.value,
      smallImageUrl: this.editForm.get(['smallImageUrl'])!.value,
      mediumImageUrl: this.editForm.get(['mediumImageUrl'])!.value,
      largeImageUrl: this.editForm.get(['largeImageUrl'])!.value,
      detailImageUrl: this.editForm.get(['detailImageUrl'])!.value,
      originalImageUrl: this.editForm.get(['originalImageUrl'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      cgst: this.editForm.get(['cgst'])!.value,
      igst: this.editForm.get(['igst'])!.value,
      sgst: this.editForm.get(['sgst'])!.value,
      price: this.editForm.get(['price'])!.value,
      cgstPercentage: this.editForm.get(['cgstPercentage'])!.value,
      igstPercentage: this.editForm.get(['igstPercentage'])!.value,
      sgstPercentage: this.editForm.get(['sgstPercentage'])!.value,
      totalPrice: this.editForm.get(['totalPrice'])!.value,
      description: this.editForm.get(['description'])!.value,
      longDescription: this.editForm.get(['longDescription'])!.value,
      info: this.editForm.get(['info'])!.value,
      isVirtual: this.editForm.get(['isVirtual'])!.value,
      isVariant: this.editForm.get(['isVariant'])!.value,
      requireInventory: this.editForm.get(['requireInventory'])!.value,
      returnable: this.editForm.get(['returnable'])!.value,
      isPopular: this.editForm.get(['isPopular'])!.value,
      changeControlNo: this.editForm.get(['changeControlNo'])!.value,
      retestDuration: this.editForm.get(['retestDuration'])!.value,
      expiryDuration: this.editForm.get(['expiryDuration'])!.value,
      validationType: this.editForm.get(['validationType'])!.value,
      shelfLife: this.editForm.get(['shelfLife'])!.value,
      productType: this.editForm.get(['productType'])!.value,
      quantityUom: this.editForm.get(['quantityUom'])!.value,
      weightUom: this.editForm.get(['weightUom'])!.value,
      sizeUom: this.editForm.get(['sizeUom'])!.value,
      uom: this.editForm.get(['uom'])!.value,
      primaryProductCategory: this.editForm.get(['primaryProductCategory'])!.value,
      virtualProduct: this.editForm.get(['virtualProduct'])!.value,
      inventoryItemType: this.editForm.get(['inventoryItemType'])!.value,
      taxSlab: this.editForm.get(['taxSlab'])!.value,
      shelfLifeUom: this.editForm.get(['shelfLifeUom'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>): void {
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
