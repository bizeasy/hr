import { element, by, ElementFinder } from 'protractor';

export class PaymentApplicationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-payment-application div table .btn-danger'));
  title = element.all(by.css('sys-payment-application div h2#page-heading span')).first();
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

export class PaymentApplicationUpdatePage {
  pageTitle = element(by.id('sys-payment-application-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  amountAppliedInput = element(by.id('field_amountApplied'));

  paymentSelect = element(by.id('field_payment'));
  invoiceSelect = element(by.id('field_invoice'));
  invoiceItemSelect = element(by.id('field_invoiceItem'));
  orderSelect = element(by.id('field_order'));
  orderItemSelect = element(by.id('field_orderItem'));
  toPaymentSelect = element(by.id('field_toPayment'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setAmountAppliedInput(amountApplied: string): Promise<void> {
    await this.amountAppliedInput.sendKeys(amountApplied);
  }

  async getAmountAppliedInput(): Promise<string> {
    return await this.amountAppliedInput.getAttribute('value');
  }

  async paymentSelectLastOption(): Promise<void> {
    await this.paymentSelect.all(by.tagName('option')).last().click();
  }

  async paymentSelectOption(option: string): Promise<void> {
    await this.paymentSelect.sendKeys(option);
  }

  getPaymentSelect(): ElementFinder {
    return this.paymentSelect;
  }

  async getPaymentSelectedOption(): Promise<string> {
    return await this.paymentSelect.element(by.css('option:checked')).getText();
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

  async invoiceItemSelectLastOption(): Promise<void> {
    await this.invoiceItemSelect.all(by.tagName('option')).last().click();
  }

  async invoiceItemSelectOption(option: string): Promise<void> {
    await this.invoiceItemSelect.sendKeys(option);
  }

  getInvoiceItemSelect(): ElementFinder {
    return this.invoiceItemSelect;
  }

  async getInvoiceItemSelectedOption(): Promise<string> {
    return await this.invoiceItemSelect.element(by.css('option:checked')).getText();
  }

  async orderSelectLastOption(): Promise<void> {
    await this.orderSelect.all(by.tagName('option')).last().click();
  }

  async orderSelectOption(option: string): Promise<void> {
    await this.orderSelect.sendKeys(option);
  }

  getOrderSelect(): ElementFinder {
    return this.orderSelect;
  }

  async getOrderSelectedOption(): Promise<string> {
    return await this.orderSelect.element(by.css('option:checked')).getText();
  }

  async orderItemSelectLastOption(): Promise<void> {
    await this.orderItemSelect.all(by.tagName('option')).last().click();
  }

  async orderItemSelectOption(option: string): Promise<void> {
    await this.orderItemSelect.sendKeys(option);
  }

  getOrderItemSelect(): ElementFinder {
    return this.orderItemSelect;
  }

  async getOrderItemSelectedOption(): Promise<string> {
    return await this.orderItemSelect.element(by.css('option:checked')).getText();
  }

  async toPaymentSelectLastOption(): Promise<void> {
    await this.toPaymentSelect.all(by.tagName('option')).last().click();
  }

  async toPaymentSelectOption(option: string): Promise<void> {
    await this.toPaymentSelect.sendKeys(option);
  }

  getToPaymentSelect(): ElementFinder {
    return this.toPaymentSelect;
  }

  async getToPaymentSelectedOption(): Promise<string> {
    return await this.toPaymentSelect.element(by.css('option:checked')).getText();
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

export class PaymentApplicationDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-paymentApplication-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-paymentApplication'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
