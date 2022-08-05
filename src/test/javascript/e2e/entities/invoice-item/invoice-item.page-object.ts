import { element, by, ElementFinder } from 'protractor';

export class InvoiceItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-invoice-item div table .btn-danger'));
  title = element.all(by.css('sys-invoice-item div h2#page-heading span')).first();
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

export class InvoiceItemUpdatePage {
  pageTitle = element(by.id('sys-invoice-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  sequenceNoInput = element(by.id('field_sequenceNo'));
  quantityInput = element(by.id('field_quantity'));
  amountInput = element(by.id('field_amount'));

  invoiceSelect = element(by.id('field_invoice'));
  invoiceItemTypeSelect = element(by.id('field_invoiceItemType'));
  fromInventoryItemSelect = element(by.id('field_fromInventoryItem'));
  productSelect = element(by.id('field_product'));
  taxAuthRateSelect = element(by.id('field_taxAuthRate'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSequenceNoInput(sequenceNo: string): Promise<void> {
    await this.sequenceNoInput.sendKeys(sequenceNo);
  }

  async getSequenceNoInput(): Promise<string> {
    return await this.sequenceNoInput.getAttribute('value');
  }

  async setQuantityInput(quantity: string): Promise<void> {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput(): Promise<string> {
    return await this.quantityInput.getAttribute('value');
  }

  async setAmountInput(amount: string): Promise<void> {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput(): Promise<string> {
    return await this.amountInput.getAttribute('value');
  }

  async invoiceSelectLastOption(): Promise<void> {
    await this.invoiceSelect.all(by.tagName('option')).last().click();
  }

  async invoiceSelectOption(option: string): Promise<void> {
    await this.invoiceSelect.sendKeys(option);
  }

  getInvoiceSelect(): ElementFinder {
    return this.invoiceSelect;
  }

  async getInvoiceSelectedOption(): Promise<string> {
    return await this.invoiceSelect.element(by.css('option:checked')).getText();
  }

  async invoiceItemTypeSelectLastOption(): Promise<void> {
    await this.invoiceItemTypeSelect.all(by.tagName('option')).last().click();
  }

  async invoiceItemTypeSelectOption(option: string): Promise<void> {
    await this.invoiceItemTypeSelect.sendKeys(option);
  }

  getInvoiceItemTypeSelect(): ElementFinder {
    return this.invoiceItemTypeSelect;
  }

  async getInvoiceItemTypeSelectedOption(): Promise<string> {
    return await this.invoiceItemTypeSelect.element(by.css('option:checked')).getText();
  }

  async fromInventoryItemSelectLastOption(): Promise<void> {
    await this.fromInventoryItemSelect.all(by.tagName('option')).last().click();
  }

  async fromInventoryItemSelectOption(option: string): Promise<void> {
    await this.fromInventoryItemSelect.sendKeys(option);
  }

  getFromInventoryItemSelect(): ElementFinder {
    return this.fromInventoryItemSelect;
  }

  async getFromInventoryItemSelectedOption(): Promise<string> {
    return await this.fromInventoryItemSelect.element(by.css('option:checked')).getText();
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

  async taxAuthRateSelectLastOption(): Promise<void> {
    await this.taxAuthRateSelect.all(by.tagName('option')).last().click();
  }

  async taxAuthRateSelectOption(option: string): Promise<void> {
    await this.taxAuthRateSelect.sendKeys(option);
  }

  getTaxAuthRateSelect(): ElementFinder {
    return this.taxAuthRateSelect;
  }

  async getTaxAuthRateSelectedOption(): Promise<string> {
    return await this.taxAuthRateSelect.element(by.css('option:checked')).getText();
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

export class InvoiceItemDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-invoiceItem-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-invoiceItem'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
