import { element, by, ElementFinder } from 'protractor';

export class ProductComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-product div table .btn-danger'));
  title = element.all(by.css('sys-product div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ProductUpdatePage {
  pageTitle = element(by.id('sys-product-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  internalNameInput = element(by.id('field_internalName'));
  brandNameInput = element(by.id('field_brandName'));
  variantInput = element(by.id('field_variant'));
  skuInput = element(by.id('field_sku'));
  introductionDateInput = element(by.id('field_introductionDate'));
  releaseDateInput = element(by.id('field_releaseDate'));
  quantityIncludedInput = element(by.id('field_quantityIncluded'));
  piecesIncludedInput = element(by.id('field_piecesIncluded'));
  weightInput = element(by.id('field_weight'));
  heightInput = element(by.id('field_height'));
  widthInput = element(by.id('field_width'));
  depthInput = element(by.id('field_depth'));
  smallImageUrlInput = element(by.id('field_smallImageUrl'));
  mediumImageUrlInput = element(by.id('field_mediumImageUrl'));
  largeImageUrlInput = element(by.id('field_largeImageUrl'));
  detailImageUrlInput = element(by.id('field_detailImageUrl'));
  originalImageUrlInput = element(by.id('field_originalImageUrl'));
  quantityInput = element(by.id('field_quantity'));
  cgstInput = element(by.id('field_cgst'));
  igstInput = element(by.id('field_igst'));
  sgstInput = element(by.id('field_sgst'));
  priceInput = element(by.id('field_price'));
  cgstPercentageInput = element(by.id('field_cgstPercentage'));
  igstPercentageInput = element(by.id('field_igstPercentage'));
  sgstPercentageInput = element(by.id('field_sgstPercentage'));
  totalPriceInput = element(by.id('field_totalPrice'));
  descriptionInput = element(by.id('field_description'));
  longDescriptionInput = element(by.id('field_longDescription'));
  infoInput = element(by.id('field_info'));
  isVirtualInput = element(by.id('field_isVirtual'));
  isVariantInput = element(by.id('field_isVariant'));
  requireInventoryInput = element(by.id('field_requireInventory'));
  returnableInput = element(by.id('field_returnable'));
  isPopularInput = element(by.id('field_isPopular'));
  changeControlNoInput = element(by.id('field_changeControlNo'));
  retestDurationInput = element(by.id('field_retestDuration'));
  expiryDurationInput = element(by.id('field_expiryDuration'));
  validationTypeInput = element(by.id('field_validationType'));
  shelfLifeInput = element(by.id('field_shelfLife'));

  productTypeSelect = element(by.id('field_productType'));
  quantityUomSelect = element(by.id('field_quantityUom'));
  weightUomSelect = element(by.id('field_weightUom'));
  sizeUomSelect = element(by.id('field_sizeUom'));
  uomSelect = element(by.id('field_uom'));
  primaryProductCategorySelect = element(by.id('field_primaryProductCategory'));
  virtualProductSelect = element(by.id('field_virtualProduct'));
  inventoryItemTypeSelect = element(by.id('field_inventoryItemType'));
  taxSlabSelect = element(by.id('field_taxSlab'));
  shelfLifeUomSelect = element(by.id('field_shelfLifeUom'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setInternalNameInput(internalName: string): Promise<void> {
    await this.internalNameInput.sendKeys(internalName);
  }

  async getInternalNameInput(): Promise<string> {
    return await this.internalNameInput.getAttribute('value');
  }

  async setBrandNameInput(brandName: string): Promise<void> {
    await this.brandNameInput.sendKeys(brandName);
  }

  async getBrandNameInput(): Promise<string> {
    return await this.brandNameInput.getAttribute('value');
  }

  async setVariantInput(variant: string): Promise<void> {
    await this.variantInput.sendKeys(variant);
  }

  async getVariantInput(): Promise<string> {
    return await this.variantInput.getAttribute('value');
  }

  async setSkuInput(sku: string): Promise<void> {
    await this.skuInput.sendKeys(sku);
  }

  async getSkuInput(): Promise<string> {
    return await this.skuInput.getAttribute('value');
  }

  async setIntroductionDateInput(introductionDate: string): Promise<void> {
    await this.introductionDateInput.sendKeys(introductionDate);
  }

  async getIntroductionDateInput(): Promise<string> {
    return await this.introductionDateInput.getAttribute('value');
  }

  async setReleaseDateInput(releaseDate: string): Promise<void> {
    await this.releaseDateInput.sendKeys(releaseDate);
  }

  async getReleaseDateInput(): Promise<string> {
    return await this.releaseDateInput.getAttribute('value');
  }

  async setQuantityIncludedInput(quantityIncluded: string): Promise<void> {
    await this.quantityIncludedInput.sendKeys(quantityIncluded);
  }

  async getQuantityIncludedInput(): Promise<string> {
    return await this.quantityIncludedInput.getAttribute('value');
  }

  async setPiecesIncludedInput(piecesIncluded: string): Promise<void> {
    await this.piecesIncludedInput.sendKeys(piecesIncluded);
  }

  async getPiecesIncludedInput(): Promise<string> {
    return await this.piecesIncludedInput.getAttribute('value');
  }

  async setWeightInput(weight: string): Promise<void> {
    await this.weightInput.sendKeys(weight);
  }

  async getWeightInput(): Promise<string> {
    return await this.weightInput.getAttribute('value');
  }

  async setHeightInput(height: string): Promise<void> {
    await this.heightInput.sendKeys(height);
  }

  async getHeightInput(): Promise<string> {
    return await this.heightInput.getAttribute('value');
  }

  async setWidthInput(width: string): Promise<void> {
    await this.widthInput.sendKeys(width);
  }

  async getWidthInput(): Promise<string> {
    return await this.widthInput.getAttribute('value');
  }

  async setDepthInput(depth: string): Promise<void> {
    await this.depthInput.sendKeys(depth);
  }

  async getDepthInput(): Promise<string> {
    return await this.depthInput.getAttribute('value');
  }

  async setSmallImageUrlInput(smallImageUrl: string): Promise<void> {
    await this.smallImageUrlInput.sendKeys(smallImageUrl);
  }

  async getSmallImageUrlInput(): Promise<string> {
    return await this.smallImageUrlInput.getAttribute('value');
  }

  async setMediumImageUrlInput(mediumImageUrl: string): Promise<void> {
    await this.mediumImageUrlInput.sendKeys(mediumImageUrl);
  }

  async getMediumImageUrlInput(): Promise<string> {
    return await this.mediumImageUrlInput.getAttribute('value');
  }

  async setLargeImageUrlInput(largeImageUrl: string): Promise<void> {
    await this.largeImageUrlInput.sendKeys(largeImageUrl);
  }

  async getLargeImageUrlInput(): Promise<string> {
    return await this.largeImageUrlInput.getAttribute('value');
  }

  async setDetailImageUrlInput(detailImageUrl: string): Promise<void> {
    await this.detailImageUrlInput.sendKeys(detailImageUrl);
  }

  async getDetailImageUrlInput(): Promise<string> {
    return await this.detailImageUrlInput.getAttribute('value');
  }

  async setOriginalImageUrlInput(originalImageUrl: string): Promise<void> {
    await this.originalImageUrlInput.sendKeys(originalImageUrl);
  }

  async getOriginalImageUrlInput(): Promise<string> {
    return await this.originalImageUrlInput.getAttribute('value');
  }

  async setQuantityInput(quantity: string): Promise<void> {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput(): Promise<string> {
    return await this.quantityInput.getAttribute('value');
  }

  async setCgstInput(cgst: string): Promise<void> {
    await this.cgstInput.sendKeys(cgst);
  }

  async getCgstInput(): Promise<string> {
    return await this.cgstInput.getAttribute('value');
  }

  async setIgstInput(igst: string): Promise<void> {
    await this.igstInput.sendKeys(igst);
  }

  async getIgstInput(): Promise<string> {
    return await this.igstInput.getAttribute('value');
  }

  async setSgstInput(sgst: string): Promise<void> {
    await this.sgstInput.sendKeys(sgst);
  }

  async getSgstInput(): Promise<string> {
    return await this.sgstInput.getAttribute('value');
  }

  async setPriceInput(price: string): Promise<void> {
    await this.priceInput.sendKeys(price);
  }

  async getPriceInput(): Promise<string> {
    return await this.priceInput.getAttribute('value');
  }

  async setCgstPercentageInput(cgstPercentage: string): Promise<void> {
    await this.cgstPercentageInput.sendKeys(cgstPercentage);
  }

  async getCgstPercentageInput(): Promise<string> {
    return await this.cgstPercentageInput.getAttribute('value');
  }

  async setIgstPercentageInput(igstPercentage: string): Promise<void> {
    await this.igstPercentageInput.sendKeys(igstPercentage);
  }

  async getIgstPercentageInput(): Promise<string> {
    return await this.igstPercentageInput.getAttribute('value');
  }

  async setSgstPercentageInput(sgstPercentage: string): Promise<void> {
    await this.sgstPercentageInput.sendKeys(sgstPercentage);
  }

  async getSgstPercentageInput(): Promise<string> {
    return await this.sgstPercentageInput.getAttribute('value');
  }

  async setTotalPriceInput(totalPrice: string): Promise<void> {
    await this.totalPriceInput.sendKeys(totalPrice);
  }

  async getTotalPriceInput(): Promise<string> {
    return await this.totalPriceInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setLongDescriptionInput(longDescription: string): Promise<void> {
    await this.longDescriptionInput.sendKeys(longDescription);
  }

  async getLongDescriptionInput(): Promise<string> {
    return await this.longDescriptionInput.getAttribute('value');
  }

  async setInfoInput(info: string): Promise<void> {
    await this.infoInput.sendKeys(info);
  }

  async getInfoInput(): Promise<string> {
    return await this.infoInput.getAttribute('value');
  }

  getIsVirtualInput(): ElementFinder {
    return this.isVirtualInput;
  }

  getIsVariantInput(): ElementFinder {
    return this.isVariantInput;
  }

  getRequireInventoryInput(): ElementFinder {
    return this.requireInventoryInput;
  }

  getReturnableInput(): ElementFinder {
    return this.returnableInput;
  }

  getIsPopularInput(): ElementFinder {
    return this.isPopularInput;
  }

  async setChangeControlNoInput(changeControlNo: string): Promise<void> {
    await this.changeControlNoInput.sendKeys(changeControlNo);
  }

  async getChangeControlNoInput(): Promise<string> {
    return await this.changeControlNoInput.getAttribute('value');
  }

  async setRetestDurationInput(retestDuration: string): Promise<void> {
    await this.retestDurationInput.sendKeys(retestDuration);
  }

  async getRetestDurationInput(): Promise<string> {
    return await this.retestDurationInput.getAttribute('value');
  }

  async setExpiryDurationInput(expiryDuration: string): Promise<void> {
    await this.expiryDurationInput.sendKeys(expiryDuration);
  }

  async getExpiryDurationInput(): Promise<string> {
    return await this.expiryDurationInput.getAttribute('value');
  }

  async setValidationTypeInput(validationType: string): Promise<void> {
    await this.validationTypeInput.sendKeys(validationType);
  }

  async getValidationTypeInput(): Promise<string> {
    return await this.validationTypeInput.getAttribute('value');
  }

  async setShelfLifeInput(shelfLife: string): Promise<void> {
    await this.shelfLifeInput.sendKeys(shelfLife);
  }

  async getShelfLifeInput(): Promise<string> {
    return await this.shelfLifeInput.getAttribute('value');
  }

  async productTypeSelectLastOption(): Promise<void> {
    await this.productTypeSelect.all(by.tagName('option')).last().click();
  }

  async productTypeSelectOption(option: string): Promise<void> {
    await this.productTypeSelect.sendKeys(option);
  }

  getProductTypeSelect(): ElementFinder {
    return this.productTypeSelect;
  }

  async getProductTypeSelectedOption(): Promise<string> {
    return await this.productTypeSelect.element(by.css('option:checked')).getText();
  }

  async quantityUomSelectLastOption(): Promise<void> {
    await this.quantityUomSelect.all(by.tagName('option')).last().click();
  }

  async quantityUomSelectOption(option: string): Promise<void> {
    await this.quantityUomSelect.sendKeys(option);
  }

  getQuantityUomSelect(): ElementFinder {
    return this.quantityUomSelect;
  }

  async getQuantityUomSelectedOption(): Promise<string> {
    return await this.quantityUomSelect.element(by.css('option:checked')).getText();
  }

  async weightUomSelectLastOption(): Promise<void> {
    await this.weightUomSelect.all(by.tagName('option')).last().click();
  }

  async weightUomSelectOption(option: string): Promise<void> {
    await this.weightUomSelect.sendKeys(option);
  }

  getWeightUomSelect(): ElementFinder {
    return this.weightUomSelect;
  }

  async getWeightUomSelectedOption(): Promise<string> {
    return await this.weightUomSelect.element(by.css('option:checked')).getText();
  }

  async sizeUomSelectLastOption(): Promise<void> {
    await this.sizeUomSelect.all(by.tagName('option')).last().click();
  }

  async sizeUomSelectOption(option: string): Promise<void> {
    await this.sizeUomSelect.sendKeys(option);
  }

  getSizeUomSelect(): ElementFinder {
    return this.sizeUomSelect;
  }

  async getSizeUomSelectedOption(): Promise<string> {
    return await this.sizeUomSelect.element(by.css('option:checked')).getText();
  }

  async uomSelectLastOption(): Promise<void> {
    await this.uomSelect.all(by.tagName('option')).last().click();
  }

  async uomSelectOption(option: string): Promise<void> {
    await this.uomSelect.sendKeys(option);
  }

  getUomSelect(): ElementFinder {
    return this.uomSelect;
  }

  async getUomSelectedOption(): Promise<string> {
    return await this.uomSelect.element(by.css('option:checked')).getText();
  }

  async primaryProductCategorySelectLastOption(): Promise<void> {
    await this.primaryProductCategorySelect.all(by.tagName('option')).last().click();
  }

  async primaryProductCategorySelectOption(option: string): Promise<void> {
    await this.primaryProductCategorySelect.sendKeys(option);
  }

  getPrimaryProductCategorySelect(): ElementFinder {
    return this.primaryProductCategorySelect;
  }

  async getPrimaryProductCategorySelectedOption(): Promise<string> {
    return await this.primaryProductCategorySelect.element(by.css('option:checked')).getText();
  }

  async virtualProductSelectLastOption(): Promise<void> {
    await this.virtualProductSelect.all(by.tagName('option')).last().click();
  }

  async virtualProductSelectOption(option: string): Promise<void> {
    await this.virtualProductSelect.sendKeys(option);
  }

  getVirtualProductSelect(): ElementFinder {
    return this.virtualProductSelect;
  }

  async getVirtualProductSelectedOption(): Promise<string> {
    return await this.virtualProductSelect.element(by.css('option:checked')).getText();
  }

  async inventoryItemTypeSelectLastOption(): Promise<void> {
    await this.inventoryItemTypeSelect.all(by.tagName('option')).last().click();
  }

  async inventoryItemTypeSelectOption(option: string): Promise<void> {
    await this.inventoryItemTypeSelect.sendKeys(option);
  }

  getInventoryItemTypeSelect(): ElementFinder {
    return this.inventoryItemTypeSelect;
  }

  async getInventoryItemTypeSelectedOption(): Promise<string> {
    return await this.inventoryItemTypeSelect.element(by.css('option:checked')).getText();
  }

  async taxSlabSelectLastOption(): Promise<void> {
    await this.taxSlabSelect.all(by.tagName('option')).last().click();
  }

  async taxSlabSelectOption(option: string): Promise<void> {
    await this.taxSlabSelect.sendKeys(option);
  }

  getTaxSlabSelect(): ElementFinder {
    return this.taxSlabSelect;
  }

  async getTaxSlabSelectedOption(): Promise<string> {
    return await this.taxSlabSelect.element(by.css('option:checked')).getText();
  }

  async shelfLifeUomSelectLastOption(): Promise<void> {
    await this.shelfLifeUomSelect.all(by.tagName('option')).last().click();
  }

  async shelfLifeUomSelectOption(option: string): Promise<void> {
    await this.shelfLifeUomSelect.sendKeys(option);
  }

  getShelfLifeUomSelect(): ElementFinder {
    return this.shelfLifeUomSelect;
  }

  async getShelfLifeUomSelectedOption(): Promise<string> {
    return await this.shelfLifeUomSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ProductDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-product-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-product'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
