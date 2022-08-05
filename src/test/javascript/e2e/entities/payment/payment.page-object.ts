import { element, by, ElementFinder } from 'protractor';

export class PaymentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-payment div table .btn-danger'));
  title = element.all(by.css('sys-payment div h2#page-heading span')).first();
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

export class PaymentUpdatePage {
  pageTitle = element(by.id('sys-payment-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  effectiveDateInput = element(by.id('field_effectiveDate'));
  paymentDateInput = element(by.id('field_paymentDate'));
  paymentRefNumberInput = element(by.id('field_paymentRefNumber'));
  amountInput = element(by.id('field_amount'));
  paymentStatusInput = element(by.id('field_paymentStatus'));
  mihpayIdInput = element(by.id('field_mihpayId'));
  emailInput = element(by.id('field_email'));
  phoneInput = element(by.id('field_phone'));
  productInfoInput = element(by.id('field_productInfo'));
  txnIdInput = element(by.id('field_txnId'));
  actualAmountInput = element(by.id('field_actualAmount'));

  paymentTypeSelect = element(by.id('field_paymentType'));
  paymentMethodTypeSelect = element(by.id('field_paymentMethodType'));
  statusSelect = element(by.id('field_status'));
  paymentMethodSelect = element(by.id('field_paymentMethod'));
  paymentGatewayResponseSelect = element(by.id('field_paymentGatewayResponse'));
  partyIdFromSelect = element(by.id('field_partyIdFrom'));
  partyIdToSelect = element(by.id('field_partyIdTo'));
  roleTypeSelect = element(by.id('field_roleType'));
  currencyUomSelect = element(by.id('field_currencyUom'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setEffectiveDateInput(effectiveDate: string): Promise<void> {
    await this.effectiveDateInput.sendKeys(effectiveDate);
  }

  async getEffectiveDateInput(): Promise<string> {
    return await this.effectiveDateInput.getAttribute('value');
  }

  async setPaymentDateInput(paymentDate: string): Promise<void> {
    await this.paymentDateInput.sendKeys(paymentDate);
  }

  async getPaymentDateInput(): Promise<string> {
    return await this.paymentDateInput.getAttribute('value');
  }

  async setPaymentRefNumberInput(paymentRefNumber: string): Promise<void> {
    await this.paymentRefNumberInput.sendKeys(paymentRefNumber);
  }

  async getPaymentRefNumberInput(): Promise<string> {
    return await this.paymentRefNumberInput.getAttribute('value');
  }

  async setAmountInput(amount: string): Promise<void> {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput(): Promise<string> {
    return await this.amountInput.getAttribute('value');
  }

  async setPaymentStatusInput(paymentStatus: string): Promise<void> {
    await this.paymentStatusInput.sendKeys(paymentStatus);
  }

  async getPaymentStatusInput(): Promise<string> {
    return await this.paymentStatusInput.getAttribute('value');
  }

  async setMihpayIdInput(mihpayId: string): Promise<void> {
    await this.mihpayIdInput.sendKeys(mihpayId);
  }

  async getMihpayIdInput(): Promise<string> {
    return await this.mihpayIdInput.getAttribute('value');
  }

  async setEmailInput(email: string): Promise<void> {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput(): Promise<string> {
    return await this.emailInput.getAttribute('value');
  }

  async setPhoneInput(phone: string): Promise<void> {
    await this.phoneInput.sendKeys(phone);
  }

  async getPhoneInput(): Promise<string> {
    return await this.phoneInput.getAttribute('value');
  }

  async setProductInfoInput(productInfo: string): Promise<void> {
    await this.productInfoInput.sendKeys(productInfo);
  }

  async getProductInfoInput(): Promise<string> {
    return await this.productInfoInput.getAttribute('value');
  }

  async setTxnIdInput(txnId: string): Promise<void> {
    await this.txnIdInput.sendKeys(txnId);
  }

  async getTxnIdInput(): Promise<string> {
    return await this.txnIdInput.getAttribute('value');
  }

  async setActualAmountInput(actualAmount: string): Promise<void> {
    await this.actualAmountInput.sendKeys(actualAmount);
  }

  async getActualAmountInput(): Promise<string> {
    return await this.actualAmountInput.getAttribute('value');
  }

  async paymentTypeSelectLastOption(): Promise<void> {
    await this.paymentTypeSelect.all(by.tagName('option')).last().click();
  }

  async paymentTypeSelectOption(option: string): Promise<void> {
    await this.paymentTypeSelect.sendKeys(option);
  }

  getPaymentTypeSelect(): ElementFinder {
    return this.paymentTypeSelect;
  }

  async getPaymentTypeSelectedOption(): Promise<string> {
    return await this.paymentTypeSelect.element(by.css('option:checked')).getText();
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

  async paymentMethodSelectLastOption(): Promise<void> {
    await this.paymentMethodSelect.all(by.tagName('option')).last().click();
  }

  async paymentMethodSelectOption(option: string): Promise<void> {
    await this.paymentMethodSelect.sendKeys(option);
  }

  getPaymentMethodSelect(): ElementFinder {
    return this.paymentMethodSelect;
  }

  async getPaymentMethodSelectedOption(): Promise<string> {
    return await this.paymentMethodSelect.element(by.css('option:checked')).getText();
  }

  async paymentGatewayResponseSelectLastOption(): Promise<void> {
    await this.paymentGatewayResponseSelect.all(by.tagName('option')).last().click();
  }

  async paymentGatewayResponseSelectOption(option: string): Promise<void> {
    await this.paymentGatewayResponseSelect.sendKeys(option);
  }

  getPaymentGatewayResponseSelect(): ElementFinder {
    return this.paymentGatewayResponseSelect;
  }

  async getPaymentGatewayResponseSelectedOption(): Promise<string> {
    return await this.paymentGatewayResponseSelect.element(by.css('option:checked')).getText();
  }

  async partyIdFromSelectLastOption(): Promise<void> {
    await this.partyIdFromSelect.all(by.tagName('option')).last().click();
  }

  async partyIdFromSelectOption(option: string): Promise<void> {
    await this.partyIdFromSelect.sendKeys(option);
  }

  getPartyIdFromSelect(): ElementFinder {
    return this.partyIdFromSelect;
  }

  async getPartyIdFromSelectedOption(): Promise<string> {
    return await this.partyIdFromSelect.element(by.css('option:checked')).getText();
  }

  async partyIdToSelectLastOption(): Promise<void> {
    await this.partyIdToSelect.all(by.tagName('option')).last().click();
  }

  async partyIdToSelectOption(option: string): Promise<void> {
    await this.partyIdToSelect.sendKeys(option);
  }

  getPartyIdToSelect(): ElementFinder {
    return this.partyIdToSelect;
  }

  async getPartyIdToSelectedOption(): Promise<string> {
    return await this.partyIdToSelect.element(by.css('option:checked')).getText();
  }

  async roleTypeSelectLastOption(): Promise<void> {
    await this.roleTypeSelect.all(by.tagName('option')).last().click();
  }

  async roleTypeSelectOption(option: string): Promise<void> {
    await this.roleTypeSelect.sendKeys(option);
  }

  getRoleTypeSelect(): ElementFinder {
    return this.roleTypeSelect;
  }

  async getRoleTypeSelectedOption(): Promise<string> {
    return await this.roleTypeSelect.element(by.css('option:checked')).getText();
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

export class PaymentDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-payment-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-payment'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
