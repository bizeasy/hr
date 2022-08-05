import { element, by, ElementFinder } from 'protractor';

export class SupplierProductComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-supplier-product div table .btn-danger'));
  title = element.all(by.css('sys-supplier-product div h2#page-heading span')).first();
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

export class SupplierProductUpdatePage {
  pageTitle = element(by.id('sys-supplier-product-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));
  minOrderQtyInput = element(by.id('field_minOrderQty'));
  lastPriceInput = element(by.id('field_lastPrice'));
  shippingPriceInput = element(by.id('field_shippingPrice'));
  supplierProductIdInput = element(by.id('field_supplierProductId'));
  leadTimeDaysInput = element(by.id('field_leadTimeDays'));
  cgstInput = element(by.id('field_cgst'));
  igstInput = element(by.id('field_igst'));
  sgstInput = element(by.id('field_sgst'));
  totalPriceInput = element(by.id('field_totalPrice'));
  commentsInput = element(by.id('field_comments'));
  supplierProductNameInput = element(by.id('field_supplierProductName'));
  manufacturerNameInput = element(by.id('field_manufacturerName'));

  productSelect = element(by.id('field_product'));
  supplierSelect = element(by.id('field_supplier'));
  quantityUomSelect = element(by.id('field_quantityUom'));
  currencyUomSelect = element(by.id('field_currencyUom'));
  manufacturerSelect = element(by.id('field_manufacturer'));

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

  async setMinOrderQtyInput(minOrderQty: string): Promise<void> {
    await this.minOrderQtyInput.sendKeys(minOrderQty);
  }

  async getMinOrderQtyInput(): Promise<string> {
    return await this.minOrderQtyInput.getAttribute('value');
  }

  async setLastPriceInput(lastPrice: string): Promise<void> {
    await this.lastPriceInput.sendKeys(lastPrice);
  }

  async getLastPriceInput(): Promise<string> {
    return await this.lastPriceInput.getAttribute('value');
  }

  async setShippingPriceInput(shippingPrice: string): Promise<void> {
    await this.shippingPriceInput.sendKeys(shippingPrice);
  }

  async getShippingPriceInput(): Promise<string> {
    return await this.shippingPriceInput.getAttribute('value');
  }

  async setSupplierProductIdInput(supplierProductId: string): Promise<void> {
    await this.supplierProductIdInput.sendKeys(supplierProductId);
  }

  async getSupplierProductIdInput(): Promise<string> {
    return await this.supplierProductIdInput.getAttribute('value');
  }

  async setLeadTimeDaysInput(leadTimeDays: string): Promise<void> {
    await this.leadTimeDaysInput.sendKeys(leadTimeDays);
  }

  async getLeadTimeDaysInput(): Promise<string> {
    return await this.leadTimeDaysInput.getAttribute('value');
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

  async setTotalPriceInput(totalPrice: string): Promise<void> {
    await this.totalPriceInput.sendKeys(totalPrice);
  }

  async getTotalPriceInput(): Promise<string> {
    return await this.totalPriceInput.getAttribute('value');
  }

  async setCommentsInput(comments: string): Promise<void> {
    await this.commentsInput.sendKeys(comments);
  }

  async getCommentsInput(): Promise<string> {
    return await this.commentsInput.getAttribute('value');
  }

  async setSupplierProductNameInput(supplierProductName: string): Promise<void> {
    await this.supplierProductNameInput.sendKeys(supplierProductName);
  }

  async getSupplierProductNameInput(): Promise<string> {
    return await this.supplierProductNameInput.getAttribute('value');
  }

  async setManufacturerNameInput(manufacturerName: string): Promise<void> {
    await this.manufacturerNameInput.sendKeys(manufacturerName);
  }

  async getManufacturerNameInput(): Promise<string> {
    return await this.manufacturerNameInput.getAttribute('value');
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

  async supplierSelectLastOption(): Promise<void> {
    await this.supplierSelect.all(by.tagName('option')).last().click();
  }

  async supplierSelectOption(option: string): Promise<void> {
    await this.supplierSelect.sendKeys(option);
  }

  getSupplierSelect(): ElementFinder {
    return this.supplierSelect;
  }

  async getSupplierSelectedOption(): Promise<string> {
    return await this.supplierSelect.element(by.css('option:checked')).getText();
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

  async currencyUomSelectLastOption(): Promise<void> {
    await this.currencyUomSelect.all(by.tagName('option')).last().click();
  }

  async currencyUomSelectOption(option: string): Promise<void> {
    await this.currencyUomSelect.sendKeys(option);
  }

  getCurrencyUomSelect(): ElementFinder {
    return this.currencyUomSelect;
  }

  async getCurrencyUomSelectedOption(): Promise<string> {
    return await this.currencyUomSelect.element(by.css('option:checked')).getText();
  }

  async manufacturerSelectLastOption(): Promise<void> {
    await this.manufacturerSelect.all(by.tagName('option')).last().click();
  }

  async manufacturerSelectOption(option: string): Promise<void> {
    await this.manufacturerSelect.sendKeys(option);
  }

  getManufacturerSelect(): ElementFinder {
    return this.manufacturerSelect;
  }

  async getManufacturerSelectedOption(): Promise<string> {
    return await this.manufacturerSelect.element(by.css('option:checked')).getText();
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

export class SupplierProductDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-supplierProduct-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-supplierProduct'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
