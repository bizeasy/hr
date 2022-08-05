import { element, by, ElementFinder } from 'protractor';

export class InvoiceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-invoice div table .btn-danger'));
  title = element.all(by.css('sys-invoice div h2#page-heading span')).first();
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

export class InvoiceUpdatePage {
  pageTitle = element(by.id('sys-invoice-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  invoiceDateInput = element(by.id('field_invoiceDate'));
  dueDateInput = element(by.id('field_dueDate'));
  paidDateInput = element(by.id('field_paidDate'));
  invoiceMessageInput = element(by.id('field_invoiceMessage'));
  referenceNumberInput = element(by.id('field_referenceNumber'));

  invoiceTypeSelect = element(by.id('field_invoiceType'));
  partyIdFromSelect = element(by.id('field_partyIdFrom'));
  partyIdToSelect = element(by.id('field_partyIdTo'));
  roleTypeSelect = element(by.id('field_roleType'));
  statusSelect = element(by.id('field_status'));
  contactMechSelect = element(by.id('field_contactMech'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setInvoiceDateInput(invoiceDate: string): Promise<void> {
    await this.invoiceDateInput.sendKeys(invoiceDate);
  }

  async getInvoiceDateInput(): Promise<string> {
    return await this.invoiceDateInput.getAttribute('value');
  }

  async setDueDateInput(dueDate: string): Promise<void> {
    await this.dueDateInput.sendKeys(dueDate);
  }

  async getDueDateInput(): Promise<string> {
    return await this.dueDateInput.getAttribute('value');
  }

  async setPaidDateInput(paidDate: string): Promise<void> {
    await this.paidDateInput.sendKeys(paidDate);
  }

  async getPaidDateInput(): Promise<string> {
    return await this.paidDateInput.getAttribute('value');
  }

  async setInvoiceMessageInput(invoiceMessage: string): Promise<void> {
    await this.invoiceMessageInput.sendKeys(invoiceMessage);
  }

  async getInvoiceMessageInput(): Promise<string> {
    return await this.invoiceMessageInput.getAttribute('value');
  }

  async setReferenceNumberInput(referenceNumber: string): Promise<void> {
    await this.referenceNumberInput.sendKeys(referenceNumber);
  }

  async getReferenceNumberInput(): Promise<string> {
    return await this.referenceNumberInput.getAttribute('value');
  }

  async invoiceTypeSelectLastOption(): Promise<void> {
    await this.invoiceTypeSelect.all(by.tagName('option')).last().click();
  }

  async invoiceTypeSelectOption(option: string): Promise<void> {
    await this.invoiceTypeSelect.sendKeys(option);
  }

  getInvoiceTypeSelect(): ElementFinder {
    return this.invoiceTypeSelect;
  }

  async getInvoiceTypeSelectedOption(): Promise<string> {
    return await this.invoiceTypeSelect.element(by.css('option:checked')).getText();
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

  async contactMechSelectLastOption(): Promise<void> {
    await this.contactMechSelect.all(by.tagName('option')).last().click();
  }

  async contactMechSelectOption(option: string): Promise<void> {
    await this.contactMechSelect.sendKeys(option);
  }

  getContactMechSelect(): ElementFinder {
    return this.contactMechSelect;
  }

  async getContactMechSelectedOption(): Promise<string> {
    return await this.contactMechSelect.element(by.css('option:checked')).getText();
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

export class InvoiceDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-invoice-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-invoice'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
