import { element, by, ElementFinder } from 'protractor';

export class PaymentGatewayResponseComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-payment-gateway-response div table .btn-danger'));
  title = element.all(by.css('sys-payment-gateway-response div h2#page-heading span')).first();
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

export class PaymentGatewayResponseUpdatePage {
  pageTitle = element(by.id('sys-payment-gateway-response-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  amountInput = element(by.id('field_amount'));
  referenceNumberInput = element(by.id('field_referenceNumber'));
  gatewayMessageInput = element(by.id('field_gatewayMessage'));

  paymentMethodTypeSelect = element(by.id('field_paymentMethodType'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setAmountInput(amount: string): Promise<void> {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput(): Promise<string> {
    return await this.amountInput.getAttribute('value');
  }

  async setReferenceNumberInput(referenceNumber: string): Promise<void> {
    await this.referenceNumberInput.sendKeys(referenceNumber);
  }

  async getReferenceNumberInput(): Promise<string> {
    return await this.referenceNumberInput.getAttribute('value');
  }

  async setGatewayMessageInput(gatewayMessage: string): Promise<void> {
    await this.gatewayMessageInput.sendKeys(gatewayMessage);
  }

  async getGatewayMessageInput(): Promise<string> {
    return await this.gatewayMessageInput.getAttribute('value');
  }

  async paymentMethodTypeSelectLastOption(): Promise<void> {
    await this.paymentMethodTypeSelect.all(by.tagName('option')).last().click();
  }

  async paymentMethodTypeSelectOption(option: string): Promise<void> {
    await this.paymentMethodTypeSelect.sendKeys(option);
  }

  getPaymentMethodTypeSelect(): ElementFinder {
    return this.paymentMethodTypeSelect;
  }

  async getPaymentMethodTypeSelectedOption(): Promise<string> {
    return await this.paymentMethodTypeSelect.element(by.css('option:checked')).getText();
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

export class PaymentGatewayResponseDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-paymentGatewayResponse-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-paymentGatewayResponse'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
