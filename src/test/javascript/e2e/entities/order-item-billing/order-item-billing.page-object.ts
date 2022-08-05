import { element, by, ElementFinder } from 'protractor';

export class OrderItemBillingComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-order-item-billing div table .btn-danger'));
  title = element.all(by.css('sys-order-item-billing div h2#page-heading span')).first();
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

export class OrderItemBillingUpdatePage {
  pageTitle = element(by.id('sys-order-item-billing-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  quantityInput = element(by.id('field_quantity'));
  amountInput = element(by.id('field_amount'));

  orderItemSelect = element(by.id('field_orderItem'));
  invoiceItemSelect = element(by.id('field_invoiceItem'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
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

export class OrderItemBillingDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-orderItemBilling-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-orderItemBilling'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
