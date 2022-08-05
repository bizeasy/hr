import { element, by, ElementFinder } from 'protractor';

export class ProductStoreFacilityComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-product-store-facility div table .btn-danger'));
  title = element.all(by.css('sys-product-store-facility div h2#page-heading span')).first();
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

export class ProductStoreFacilityUpdatePage {
  pageTitle = element(by.id('sys-product-store-facility-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));
  sequenceNoInput = element(by.id('field_sequenceNo'));

  productStoreSelect = element(by.id('field_productStore'));
  facilitySelect = element(by.id('field_facility'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFromDateInput(fromDate: string): Promise<void> {
    await this.fromDateInput.sendKeys(fromDate);
  }

  async getFromDateInput(): Promise<string> {
    return await this.fromDateInput.getAttribute('value');
  }

  async setThruDateInput(thruDate: string): Promise<void> {
    await this.thruDateInput.sendKeys(thruDate);
  }

  async getThruDateInput(): Promise<string> {
    return await this.thruDateInput.getAttribute('value');
  }

  async setSequenceNoInput(sequenceNo: string): Promise<void> {
    await this.sequenceNoInput.sendKeys(sequenceNo);
  }

  async getSequenceNoInput(): Promise<string> {
    return await this.sequenceNoInput.getAttribute('value');
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

export class ProductStoreFacilityDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-productStoreFacility-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-productStoreFacility'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
