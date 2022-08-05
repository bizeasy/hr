import { element, by, ElementFinder } from 'protractor';

export class ProductPriceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-product-price div table .btn-danger'));
  title = element.all(by.css('sys-product-price div h2#page-heading span')).first();
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

export class ProductPriceUpdatePage {
  pageTitle = element(by.id('sys-product-price-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));
  priceInput = element(by.id('field_price'));
  cgstInput = element(by.id('field_cgst'));
  igstInput = element(by.id('field_igst'));
  sgstInput = element(by.id('field_sgst'));
  totalPriceInput = element(by.id('field_totalPrice'));

  productSelect = element(by.id('field_product'));
  productPriceTypeSelect = element(by.id('field_productPriceType'));
  productPricePurposeSelect = element(by.id('field_productPricePurpose'));
  currencyUomSelect = element(by.id('field_currencyUom'));

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

  async setPriceInput(price: string): Promise<void> {
    await this.priceInput.sendKeys(price);
  }

  async getPriceInput(): Promise<string> {
    return await this.priceInput.getAttribute('value');
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

  async productPriceTypeSelectLastOption(): Promise<void> {
    await this.productPriceTypeSelect.all(by.tagName('option')).last().click();
  }

  async productPriceTypeSelectOption(option: string): Promise<void> {
    await this.productPriceTypeSelect.sendKeys(option);
  }

  getProductPriceTypeSelect(): ElementFinder {
    return this.productPriceTypeSelect;
  }

  async getProductPriceTypeSelectedOption(): Promise<string> {
    return await this.productPriceTypeSelect.element(by.css('option:checked')).getText();
  }

  async productPricePurposeSelectLastOption(): Promise<void> {
    await this.productPricePurposeSelect.all(by.tagName('option')).last().click();
  }

  async productPricePurposeSelectOption(option: string): Promise<void> {
    await this.productPricePurposeSelect.sendKeys(option);
  }

  getProductPricePurposeSelect(): ElementFinder {
    return this.productPricePurposeSelect;
  }

  async getProductPricePurposeSelectedOption(): Promise<string> {
    return await this.productPricePurposeSelect.element(by.css('option:checked')).getText();
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

export class ProductPriceDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-productPrice-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-productPrice'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
