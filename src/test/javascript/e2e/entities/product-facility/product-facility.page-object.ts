import { element, by, ElementFinder } from 'protractor';

export class ProductFacilityComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-product-facility div table .btn-danger'));
  title = element.all(by.css('sys-product-facility div h2#page-heading span')).first();
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

export class ProductFacilityUpdatePage {
  pageTitle = element(by.id('sys-product-facility-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  minimumStockInput = element(by.id('field_minimumStock'));
  reorderQtyInput = element(by.id('field_reorderQty'));
  daysToShipInput = element(by.id('field_daysToShip'));
  lastInventoryCountInput = element(by.id('field_lastInventoryCount'));

  productSelect = element(by.id('field_product'));
  facilitySelect = element(by.id('field_facility'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMinimumStockInput(minimumStock: string): Promise<void> {
    await this.minimumStockInput.sendKeys(minimumStock);
  }

  async getMinimumStockInput(): Promise<string> {
    return await this.minimumStockInput.getAttribute('value');
  }

  async setReorderQtyInput(reorderQty: string): Promise<void> {
    await this.reorderQtyInput.sendKeys(reorderQty);
  }

  async getReorderQtyInput(): Promise<string> {
    return await this.reorderQtyInput.getAttribute('value');
  }

  async setDaysToShipInput(daysToShip: string): Promise<void> {
    await this.daysToShipInput.sendKeys(daysToShip);
  }

  async getDaysToShipInput(): Promise<string> {
    return await this.daysToShipInput.getAttribute('value');
  }

  async setLastInventoryCountInput(lastInventoryCount: string): Promise<void> {
    await this.lastInventoryCountInput.sendKeys(lastInventoryCount);
  }

  async getLastInventoryCountInput(): Promise<string> {
    return await this.lastInventoryCountInput.getAttribute('value');
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

export class ProductFacilityDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-productFacility-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-productFacility'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
