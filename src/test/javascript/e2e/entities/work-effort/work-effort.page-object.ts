import { element, by, ElementFinder } from 'protractor';

export class WorkEffortComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-work-effort div table .btn-danger'));
  title = element.all(by.css('sys-work-effort div h2#page-heading span')).first();
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

export class WorkEffortUpdatePage {
  pageTitle = element(by.id('sys-work-effort-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  codeInput = element(by.id('field_code'));
  batchSizeInput = element(by.id('field_batchSize'));
  minYieldRangeInput = element(by.id('field_minYieldRange'));
  maxYieldRangeInput = element(by.id('field_maxYieldRange'));
  percentCompleteInput = element(by.id('field_percentComplete'));
  validationTypeInput = element(by.id('field_validationType'));
  shelfLifeInput = element(by.id('field_shelfLife'));
  outputQtyInput = element(by.id('field_outputQty'));

  productSelect = element(by.id('field_product'));
  ksmSelect = element(by.id('field_ksm'));
  typeSelect = element(by.id('field_type'));
  purposeSelect = element(by.id('field_purpose'));
  statusSelect = element(by.id('field_status'));
  facilitySelect = element(by.id('field_facility'));
  shelfListUomSelect = element(by.id('field_shelfListUom'));
  productCategorySelect = element(by.id('field_productCategory'));
  productStoreSelect = element(by.id('field_productStore'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setCodeInput(code: string): Promise<void> {
    await this.codeInput.sendKeys(code);
  }

  async getCodeInput(): Promise<string> {
    return await this.codeInput.getAttribute('value');
  }

  async setBatchSizeInput(batchSize: string): Promise<void> {
    await this.batchSizeInput.sendKeys(batchSize);
  }

  async getBatchSizeInput(): Promise<string> {
    return await this.batchSizeInput.getAttribute('value');
  }

  async setMinYieldRangeInput(minYieldRange: string): Promise<void> {
    await this.minYieldRangeInput.sendKeys(minYieldRange);
  }

  async getMinYieldRangeInput(): Promise<string> {
    return await this.minYieldRangeInput.getAttribute('value');
  }

  async setMaxYieldRangeInput(maxYieldRange: string): Promise<void> {
    await this.maxYieldRangeInput.sendKeys(maxYieldRange);
  }

  async getMaxYieldRangeInput(): Promise<string> {
    return await this.maxYieldRangeInput.getAttribute('value');
  }

  async setPercentCompleteInput(percentComplete: string): Promise<void> {
    await this.percentCompleteInput.sendKeys(percentComplete);
  }

  async getPercentCompleteInput(): Promise<string> {
    return await this.percentCompleteInput.getAttribute('value');
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

  async setOutputQtyInput(outputQty: string): Promise<void> {
    await this.outputQtyInput.sendKeys(outputQty);
  }

  async getOutputQtyInput(): Promise<string> {
    return await this.outputQtyInput.getAttribute('value');
  }

  async productSelectLastOption(): Promise<void> {
    await this.productSelect.all(by.tagName('option')).last().click();
  }

  async productSelectOption(option: string): Promise<void> {
    await this.productSelect.sendKeys(option);
  }

  getProductSelect(): ElementFinder {
    return this.productSelect;
  }

  async getProductSelectedOption(): Promise<string> {
    return await this.productSelect.element(by.css('option:checked')).getText();
  }

  async ksmSelectLastOption(): Promise<void> {
    await this.ksmSelect.all(by.tagName('option')).last().click();
  }

  async ksmSelectOption(option: string): Promise<void> {
    await this.ksmSelect.sendKeys(option);
  }

  getKsmSelect(): ElementFinder {
    return this.ksmSelect;
  }

  async getKsmSelectedOption(): Promise<string> {
    return await this.ksmSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption(): Promise<void> {
    await this.typeSelect.all(by.tagName('option')).last().click();
  }

  async typeSelectOption(option: string): Promise<void> {
    await this.typeSelect.sendKeys(option);
  }

  getTypeSelect(): ElementFinder {
    return this.typeSelect;
  }

  async getTypeSelectedOption(): Promise<string> {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async purposeSelectLastOption(): Promise<void> {
    await this.purposeSelect.all(by.tagName('option')).last().click();
  }

  async purposeSelectOption(option: string): Promise<void> {
    await this.purposeSelect.sendKeys(option);
  }

  getPurposeSelect(): ElementFinder {
    return this.purposeSelect;
  }

  async getPurposeSelectedOption(): Promise<string> {
    return await this.purposeSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }

  async statusSelectOption(option: string): Promise<void> {
    await this.statusSelect.sendKeys(option);
  }

  getStatusSelect(): ElementFinder {
    return this.statusSelect;
  }

  async getStatusSelectedOption(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async facilitySelectLastOption(): Promise<void> {
    await this.facilitySelect.all(by.tagName('option')).last().click();
  }

  async facilitySelectOption(option: string): Promise<void> {
    await this.facilitySelect.sendKeys(option);
  }

  getFacilitySelect(): ElementFinder {
    return this.facilitySelect;
  }

  async getFacilitySelectedOption(): Promise<string> {
    return await this.facilitySelect.element(by.css('option:checked')).getText();
  }

  async shelfListUomSelectLastOption(): Promise<void> {
    await this.shelfListUomSelect.all(by.tagName('option')).last().click();
  }

  async shelfListUomSelectOption(option: string): Promise<void> {
    await this.shelfListUomSelect.sendKeys(option);
  }

  getShelfListUomSelect(): ElementFinder {
    return this.shelfListUomSelect;
  }

  async getShelfListUomSelectedOption(): Promise<string> {
    return await this.shelfListUomSelect.element(by.css('option:checked')).getText();
  }

  async productCategorySelectLastOption(): Promise<void> {
    await this.productCategorySelect.all(by.tagName('option')).last().click();
  }

  async productCategorySelectOption(option: string): Promise<void> {
    await this.productCategorySelect.sendKeys(option);
  }

  getProductCategorySelect(): ElementFinder {
    return this.productCategorySelect;
  }

  async getProductCategorySelectedOption(): Promise<string> {
    return await this.productCategorySelect.element(by.css('option:checked')).getText();
  }

  async productStoreSelectLastOption(): Promise<void> {
    await this.productStoreSelect.all(by.tagName('option')).last().click();
  }

  async productStoreSelectOption(option: string): Promise<void> {
    await this.productStoreSelect.sendKeys(option);
  }

  getProductStoreSelect(): ElementFinder {
    return this.productStoreSelect;
  }

  async getProductStoreSelectedOption(): Promise<string> {
    return await this.productStoreSelect.element(by.css('option:checked')).getText();
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

export class WorkEffortDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-workEffort-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-workEffort'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
